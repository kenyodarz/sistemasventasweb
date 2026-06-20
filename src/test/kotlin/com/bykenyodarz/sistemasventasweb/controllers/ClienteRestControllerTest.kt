package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Cliente
import com.bykenyodarz.sistemasventasweb.services.apis.ClienteServiceAPI
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
        val cliente = Cliente().apply { idCliente = 1 }
        `when`(service.getAll()).thenReturn(listOf(cliente))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(cliente), response.body)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val cliente = Cliente().apply { idCliente = 1 }
        `when`(service.getOne(1)).thenReturn(cliente)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(cliente, response.body)
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
        val cliente = Cliente().apply { idCliente = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(cliente)).thenReturn(cliente)

        val response = controller.save(cliente, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(cliente, response.body)
        verify(service).save(cliente)
    }

    @Test
    fun testSaveValidationError() {
        val cliente = Cliente().apply { idCliente = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("cliente", "dni", "must not be blank")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(cliente, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo dni must not be blank", body["dni"])
        verify(service, never()).save(anyNonNullCliente())
    }

    @Test
    fun testDeleteFound() {
        val cliente = Cliente().apply { idCliente = 1 }
        `when`(service.getOne(1)).thenReturn(cliente)
        doNothing().`when`(service).delete(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(cliente, response.body)
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
    fun testFindByDniFound() {
        val cliente = Cliente().apply { dni = "12345" }
        `when`(service.findByDni("12345")).thenReturn(cliente)

        val response = controller.findByDni("12345")
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(cliente, response.body)
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
