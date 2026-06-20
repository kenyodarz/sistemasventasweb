package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Venta
import com.bykenyodarz.sistemasventasweb.services.apis.VentaServiceAPI
import com.bykenyodarz.sistemasventasweb.util.GenerarSerie
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

class VentasRestControllerTest {

    private lateinit var service: VentaServiceAPI
    private lateinit var controller: VentasRestController

    @BeforeEach
    fun setUp() {
        service = mock(VentaServiceAPI::class.java)
        controller = VentasRestController(service)
    }

    @Test
    fun testGetAll() {
        val venta = Venta().apply { idVenta = 1 }
        `when`(service.getAll()).thenReturn(listOf(venta))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(venta), response.body)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val venta = Venta().apply { idVenta = 1 }
        `when`(service.getOne(1)).thenReturn(venta)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(venta, response.body)
        verify(service).getOne(1)
    }

    @Test
    fun testGetOneNotFound() {
        `when`(service.getOne(1)).thenReturn(null)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Recurso no encontrado", response.body)
        verify(service).getOne(1)
    }

    @Test
    fun testSaveSuccess() {
        val venta = Venta().apply { idVenta = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(venta)).thenReturn(venta)

        val response = controller.save(venta, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(venta, response.body)
        verify(service).save(venta)
    }

    @Test
    fun testSaveValidationError() {
        val venta = Venta().apply { idVenta = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("venta", "numeroSerie", "must not be blank")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(venta, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo numeroSerie must not be blank", body["numeroSerie"])
        verify(service, never()).save(anyNonNullVenta())
    }

    @Test
    fun testDeleteFound() {
        val venta = Venta().apply { idVenta = 1 }
        `when`(service.getOne(1)).thenReturn(venta)
        doNothing().`when`(service).delete(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(venta, response.body)
        verify(service).getOne(1)
        verify(service).delete(1)
    }

    @Test
    fun testDeleteNotFound() {
        `when`(service.getOne(1)).thenReturn(null)

        val response = controller.delete(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(service).getOne(1)
        verify(service, never()).delete(anyInt())
    }

    @Test
    fun testObtenerNumeroSerie() {
        `when`(service.findMAxNumeroSerie()).thenReturn(15)

        val response = controller.obtenerNumeroSerie()
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as GenerarSerie
        assertEquals(16, body.dato)
        assertEquals("00000016", body.numero)
        verify(service).findMAxNumeroSerie()
    }

    private fun anyNonNullVenta(): Venta {
        any<Venta>()
        return Venta()
    }
}
