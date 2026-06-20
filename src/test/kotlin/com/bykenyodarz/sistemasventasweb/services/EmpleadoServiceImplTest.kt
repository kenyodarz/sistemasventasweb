package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Empleado
import com.bykenyodarz.sistemasventasweb.repositories.EmpleadoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class EmpleadoServiceImplTest {

    private lateinit var repository: EmpleadoRepository
    private lateinit var service: EmpleadoServiceImpl

    @BeforeEach
    fun setUp() {
        repository = mock(EmpleadoRepository::class.java)
        service = EmpleadoServiceImpl(repository)
    }

    @Test
    fun testGetRepository() {
        assertEquals(repository, service.getRepository())
    }

    @Test
    fun testGetAll() {
        val empleado = Empleado().apply { idEmpleado = 1; user = "admin" }
        `when`(repository.findAll()).thenReturn(listOf(empleado))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(empleado, result[0])
        verify(repository).findAll()
    }

    @Test
    fun testGetOneFound() {
        val empleado = Empleado().apply { idEmpleado = 1 }
        `when`(repository.findById(1)).thenReturn(Optional.of(empleado))

        val result = service.getOne(1)
        assertNotNull(result)
        assertEquals(empleado, result)
        verify(repository).findById(1)
    }

    @Test
    fun testGetOneNotFound() {
        `when`(repository.findById(1)).thenReturn(Optional.empty())

        val result = service.getOne(1)
        assertNull(result)
        verify(repository).findById(1)
    }

    @Test
    fun testSave() {
        val empleado = Empleado().apply { idEmpleado = 1 }
        `when`(repository.save(empleado)).thenReturn(empleado)

        val result = service.save(empleado)
        assertEquals(empleado, result)
        verify(repository).save(empleado)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.delete(1)
        verify(repository).deleteById(1)
    }

    @Test
    fun testFindEmpleadoByUserAndDniFound() {
        val empleado = Empleado().apply { user = "user1"; dni = "123" }
        `when`(repository.findEmpleadoByUserAndDni("user1", "123")).thenReturn(Optional.of(empleado))

        val result = service.findEmpleadoByUserAndDni("user1", "123")
        assertNotNull(result)
        assertEquals(empleado, result)
        verify(repository).findEmpleadoByUserAndDni("user1", "123")
    }

    @Test
    fun testFindEmpleadoByUserAndDniNotFound() {
        `when`(repository.findEmpleadoByUserAndDni("user1", "123")).thenReturn(Optional.empty())

        val result = service.findEmpleadoByUserAndDni("user1", "123")
        assertNull(result)
        verify(repository).findEmpleadoByUserAndDni("user1", "123")
    }
}
