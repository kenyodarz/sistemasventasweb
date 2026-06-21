package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.VentaServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.Venta
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateVentaRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.VentaResponse
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
        val venta = Venta(idVenta = 1, numeroSerie = "00000001", monto = 100.0, estado = "1")
        `when`(service.getAll()).thenReturn(listOf(venta))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as List<*>
        assertEquals(1, body.size)
        val responseDto = body[0] as VentaResponse
        assertEquals(1, responseDto.idVenta)
        assertEquals("00000001", responseDto.numeroSerie)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val venta = Venta(idVenta = 1, numeroSerie = "00000001")
        `when`(service.getById(1)).thenReturn(venta)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as VentaResponse
        assertEquals(1, body.idVenta)
        verify(service).getById(1)
    }

    @Test
    fun testGetOneNotFound() {
        `when`(service.getById(1)).thenReturn(null)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Recurso no encontrado", response.body)
        verify(service).getById(1)
    }

    @Test
    fun testSaveSuccess() {
        val request = CreateVentaRequest(numeroSerie = "00000001", monto = 100.0, estado = "1")
        val venta = Venta(idVenta = 1, numeroSerie = "00000001", monto = 100.0, estado = "1")
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(anyNonNullVenta())).thenReturn(venta)

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val body = response.body as VentaResponse
        assertEquals(1, body.idVenta)
        verify(service).save(anyNonNullVenta())
    }

    @Test
    fun testSaveValidationError() {
        val request = CreateVentaRequest()
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("request", "numeroSerie", "must not be blank")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo numeroSerie must not be blank", body["numeroSerie"])
        verify(service, never()).save(anyNonNullVenta())
    }

    @Test
    fun testDeleteFound() {
        val venta = Venta(idVenta = 1)
        `when`(service.getById(1)).thenReturn(venta)
        doNothing().`when`(service).deleteById(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as VentaResponse
        assertEquals(1, body.idVenta)
        verify(service).getById(1)
        verify(service).deleteById(1)
    }

    @Test
    fun testDeleteNotFound() {
        `when`(service.getById(1)).thenReturn(null)

        val response = controller.delete(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(service).getById(1)
        verify(service, never()).deleteById(anyInt())
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
