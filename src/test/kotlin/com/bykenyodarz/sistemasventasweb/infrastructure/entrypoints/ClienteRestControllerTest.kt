package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.ClienteServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateClienteRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.ClienteResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

class ClienteRestControllerTest {

    private lateinit var service: ClienteServiceAPI
    private lateinit var controller: ClienteRestController

    @BeforeEach
    fun setUp() {
        service = mock(ClienteServiceAPI::class.java)
        controller = ClienteRestController(service)
    }

    @Test
    fun testGetAll() {
        val cliente = Cliente(idCliente = 1)
        `when`(service.getAll()).thenReturn(listOf(cliente))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as List<*>
        assertEquals(1, body.size)
        val responseDto = body[0] as ClienteResponse
        assertEquals(1, responseDto.idCliente)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val cliente = Cliente(idCliente = 1)
        `when`(service.getById(1)).thenReturn(cliente)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as ClienteResponse
        assertEquals(1, body.idCliente)
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
        val request = CreateClienteRequest(dni = "123", nombres = "Nombres")
        val cliente = Cliente(idCliente = 1, dni = "123", nombres = "Nombres")
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(anyNonNullCliente())).thenReturn(cliente)

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val body = response.body as ClienteResponse
        assertEquals(1, body.idCliente)
        verify(service).save(anyNonNullCliente())
    }

    @Test
    fun testSaveValidationError() {
        val request = CreateClienteRequest()
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("request", "dni", "must not be blank")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo dni must not be blank", body["dni"])
        verify(service, never()).save(anyNonNullCliente())
    }

    @Test
    fun testDeleteFound() {
        val cliente = Cliente(idCliente = 1)
        `when`(service.getById(1)).thenReturn(cliente)
        doNothing().`when`(service).deleteById(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as ClienteResponse
        assertEquals(1, body.idCliente)
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
    fun testFindByDniFound() {
        val cliente = Cliente(dni = "12345")
        `when`(service.findByDni("12345")).thenReturn(cliente)

        val response = controller.findByDni("12345")
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as ClienteResponse
        assertEquals("12345", body.dni)
        verify(service).findByDni("12345")
    }

    @Test
    fun testFindByDniNotFound() {
        `when`(service.findByDni("12345")).thenReturn(null)

        val response = controller.findByDni("12345")
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Cliente no Encontrado", response.body)
        verify(service).findByDni("12345")
    }

    private fun anyNonNullCliente(): Cliente {
        any<Cliente>()
        return Cliente()
    }
}
