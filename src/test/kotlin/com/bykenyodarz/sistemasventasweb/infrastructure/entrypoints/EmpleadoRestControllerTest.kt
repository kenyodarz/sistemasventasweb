package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.EmpleadoServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateEmpleadoRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.EmpleadoResponse
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
        val empleado = Empleado(idEmpleado = 1, dni = "123", nombres = "Empleado 1", telefono = "456", estado = "1", user = "emp1")
        `when`(service.getAll()).thenReturn(listOf(empleado))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as List<*>
        assertEquals(1, body.size)
        val responseDto = body[0] as EmpleadoResponse
        assertEquals(1, responseDto.idEmpleado)
        assertEquals("Empleado 1", responseDto.nombres)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val empleado = Empleado(idEmpleado = 1, dni = "123", nombres = "Empleado 1", telefono = "456", estado = "1", user = "emp1")
        `when`(service.getById(1)).thenReturn(empleado)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as EmpleadoResponse
        assertEquals(1, body.idEmpleado)
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
        val request = CreateEmpleadoRequest(dni = "123", nombres = "Nuevo Empleado", telefono = "456", estado = "1", user = "emp_new")
        val empleado = Empleado(idEmpleado = 1, dni = "123", nombres = "Nuevo Empleado", telefono = "456", estado = "1", user = "emp_new")
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(anyNonNullEmpleado())).thenReturn(empleado)

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val body = response.body as EmpleadoResponse
        assertEquals(1, body.idEmpleado)
        verify(service).save(anyNonNullEmpleado())
    }

    @Test
    fun testSaveValidationError() {
        val request = CreateEmpleadoRequest()
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("request", "nombres", "must not be blank")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo nombres must not be blank", body["nombres"])
        verify(service, never()).save(anyNonNullEmpleado())
    }

    @Test
    fun testDeleteFound() {
        val empleado = Empleado(idEmpleado = 1)
        `when`(service.getById(1)).thenReturn(empleado)
        doNothing().`when`(service).deleteById(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as EmpleadoResponse
        assertEquals(1, body.idEmpleado)
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
    fun testValidarEmpleadoFound() {
        val empleado = Empleado(idEmpleado = 1, user = "user1", dni = "123")
        `when`(service.findEmpleadoByUserAndDni("user1", "123")).thenReturn(empleado)

        val response = controller.validarEmpleado("user1", "123")
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as EmpleadoResponse
        assertEquals("user1", body.user)
        assertEquals("123", body.dni)
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
