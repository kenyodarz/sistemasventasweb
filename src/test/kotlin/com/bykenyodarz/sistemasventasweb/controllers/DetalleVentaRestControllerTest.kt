package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.DetalleVenta
import com.bykenyodarz.sistemasventasweb.services.apis.DetalleVentaServiceAPI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

class DetalleVentaRestControllerTest {

    private lateinit var service: DetalleVentaServiceAPI
    private lateinit var controller: DetalleVentaRestController

    @BeforeEach
    fun setUp() {
        service = mock(DetalleVentaServiceAPI::class.java)
        controller = DetalleVentaRestController(service)
    }

    @Test
    fun testGetAll() {
        val detalle = DetalleVenta().apply { idDetalleVenta = 1 }
        `when`(service.getAll()).thenReturn(listOf(detalle))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(detalle), response.body)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val detalle = DetalleVenta().apply { idDetalleVenta = 1 }
        `when`(service.getOne(1)).thenReturn(detalle)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(detalle, response.body)
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
        val detalle = DetalleVenta().apply { idDetalleVenta = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(detalle)).thenReturn(detalle)

        val response = controller.save(detalle, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(detalle, response.body)
        verify(service).save(detalle)
    }

    @Test
    fun testSaveValidationError() {
        val detalle = DetalleVenta().apply { idDetalleVenta = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("detalle", "cantidad", "must be greater than 0")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(detalle, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo cantidad must be greater than 0", body["cantidad"])
        verify(service, never()).save(anyNonNullDetalle())
    }

    @Test
    fun testDeleteFound() {
        val detalle = DetalleVenta().apply { idDetalleVenta = 1 }
        `when`(service.getOne(1)).thenReturn(detalle)
        doNothing().`when`(service).delete(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(detalle, response.body)
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

    private fun anyNonNullDetalle(): DetalleVenta {
        any<DetalleVenta>()
        return DetalleVenta()
    }
}
