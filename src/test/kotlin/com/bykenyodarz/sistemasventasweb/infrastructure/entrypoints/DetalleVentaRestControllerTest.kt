package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.DetalleVentaServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.DetalleVenta
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateDetalleVentaRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.DetalleVentaResponse
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
        val detalle = DetalleVenta(idDetalleVenta = 1, cantidad = 5, precioVenta = 15.0)
        `when`(service.getAll()).thenReturn(listOf(detalle))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as List<*>
        assertEquals(1, body.size)
        val responseDto = body[0] as DetalleVentaResponse
        assertEquals(1, responseDto.idDetalleVenta)
        assertEquals(5, responseDto.cantidad)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val detalle = DetalleVenta(idDetalleVenta = 1, cantidad = 5, precioVenta = 15.0)
        `when`(service.getById(1)).thenReturn(detalle)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as DetalleVentaResponse
        assertEquals(1, body.idDetalleVenta)
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
        val request = CreateDetalleVentaRequest(cantidad = 5, precioVenta = 15.0)
        val detalle = DetalleVenta(idDetalleVenta = 1, cantidad = 5, precioVenta = 15.0)
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(anyNonNullDetalle())).thenReturn(detalle)

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val body = response.body as DetalleVentaResponse
        assertEquals(1, body.idDetalleVenta)
        verify(service).save(anyNonNullDetalle())
    }

    @Test
    fun testSaveValidationError() {
        val request = CreateDetalleVentaRequest()
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("request", "cantidad", "must be greater than 0")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo cantidad must be greater than 0", body["cantidad"])
        verify(service, never()).save(anyNonNullDetalle())
    }

    @Test
    fun testDeleteFound() {
        val detalle = DetalleVenta(idDetalleVenta = 1)
        `when`(service.getById(1)).thenReturn(detalle)
        doNothing().`when`(service).deleteById(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as DetalleVentaResponse
        assertEquals(1, body.idDetalleVenta)
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

    private fun anyNonNullDetalle(): DetalleVenta {
        any<DetalleVenta>()
        return DetalleVenta()
    }
}
