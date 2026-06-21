package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.domain.ports.EmpleadoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class EmpleadoServiceTest {

    private lateinit var repository: EmpleadoRepository
    private lateinit var service: EmpleadoService

    @BeforeEach
    fun setUp() {
        repository = mock(EmpleadoRepository::class.java)
        service = EmpleadoService(repository)
    }

    @Test
    fun testGetAll() {
        val empleado = Empleado(idEmpleado = 1, nombres = "Carlos")
        `when`(repository.getAll()).thenReturn(listOf(empleado))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(empleado, result[0])
        verify(repository).getAll()
    }

    @Test
    fun testGetByIdFound() {
        val empleado = Empleado(idEmpleado = 1)
        `when`(repository.getById(1)).thenReturn(empleado)

        val result = service.getById(1)
        assertNotNull(result)
        assertEquals(empleado, result)
        verify(repository).getById(1)
    }

    @Test
    fun testGetByIdNotFound() {
        `when`(repository.getById(1)).thenReturn(null)

        val result = service.getById(1)
        assertNull(result)
        verify(repository).getById(1)
    }

    @Test
    fun testSave() {
        val empleado = Empleado(idEmpleado = 1)
        `when`(repository.save(empleado)).thenReturn(empleado)

        val result = service.save(empleado)
        assertEquals(empleado, result)
        verify(repository).save(empleado)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.deleteById(1)
        verify(repository).deleteById(1)
    }

    @Test
    fun testFindEmpleadoByUserAndDniFound() {
        val empleado = Empleado(user = "admin", dni = "123")
        `when`(repository.findEmpleadoByUserAndDni("admin", "123")).thenReturn(empleado)

        val result = service.findEmpleadoByUserAndDni("admin", "123")
        assertNotNull(result)
        assertEquals(empleado, result)
        verify(repository).findEmpleadoByUserAndDni("admin", "123")
    }

    @Test
    fun testFindEmpleadoByUserAndDniNotFound() {
        `when`(repository.findEmpleadoByUserAndDni("admin", "123")).thenReturn(null)

        val result = service.findEmpleadoByUserAndDni("admin", "123")
        assertNull(result)
        verify(repository).findEmpleadoByUserAndDni("admin", "123")
    }
}
