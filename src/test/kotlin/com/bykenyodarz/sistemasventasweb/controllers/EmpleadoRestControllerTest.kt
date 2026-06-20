package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Empleado
import com.bykenyodarz.sistemasventasweb.services.apis.EmpleadoServiceAPI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

class EmpleadoRestControllerTest {

    private lateinit var service: EmpleadoServiceAPI
    private lateinit var controller: EmpleadoRestController

    @BeforeEach
    fun setUp() {
        service = mock(EmpleadoServiceAPI::class.java)
        controller = EmpleadoRestController(service)
    }

    @Test
    fun testGetAll() {
        val empleado = Empleado().apply { idEmpleado = 1 }
        `when`(service.getAll()).thenReturn(listOf(empleado))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(empleado), response.body)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val empleado = Empleado().apply { idEmpleado = 1 }
        `when`(service.getOne(1)).thenReturn(empleado)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(empleado, response.body)
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
        val empleado = Empleado().apply { idEmpleado = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(empleado)).thenReturn(empleado)

        val response = controller.save(empleado, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(empleado, response.body)
        verify(service).save(empleado)
    }

    @Test
    fun testSaveValidationError() {
        val empleado = Empleado().apply { idEmpleado = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("empleado", "nombres", "must not be blank")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(empleado, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo nombres must not be blank", body["nombres"])
        verify(service, never()).save(anyNonNullEmpleado())
    }

    @Test
    fun testDeleteFound() {
        val empleado = Empleado().apply { idEmpleado = 1 }
        `when`(service.getOne(1)).thenReturn(empleado)
        doNothing().`when`(service).delete(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(empleado, response.body)
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
    fun testValidarEmpleadoFound() {
        val empleado = Empleado().apply { user = "user1"; dni = "123" }
        `when`(service.findEmpleadoByUserAndDni("user1", "123")).thenReturn(empleado)

        val response = controller.validarEmpleado("user1", "123")
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(empleado, response.body)
        verify(service).findEmpleadoByUserAndDni("user1", "123")
    }

    @Test
    fun testValidarEmpleadoNotFound() {
        `when`(service.findEmpleadoByUserAndDni("user1", "123")).thenReturn(null)

        val response = controller.validarEmpleado("user1", "123")
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Usuario no Encontrado", response.body)
        verify(service).findEmpleadoByUserAndDni("user1", "123")
    }

    private fun anyNonNullEmpleado(): Empleado {
        any<Empleado>()
        return Empleado()
    }
}
