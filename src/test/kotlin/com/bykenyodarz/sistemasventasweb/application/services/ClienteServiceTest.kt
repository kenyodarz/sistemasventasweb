package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.domain.ports.ClienteRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class ClienteServiceTest {

    private lateinit var repository: ClienteRepository
    private lateinit var service: ClienteService

    @BeforeEach
    fun setUp() {
        repository = mock(ClienteRepository::class.java)
        service = ClienteService(repository)
    }

    @Test
    fun testGetAll() {
        val cliente = Cliente(idCliente = 1, dni = "12345678")
        `when`(repository.getAll()).thenReturn(listOf(cliente))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(cliente, result[0])
        verify(repository).getAll()
    }

    @Test
    fun testGetByIdFound() {
        val cliente = Cliente(idCliente = 1)
        `when`(repository.getById(1)).thenReturn(cliente)

        val result = service.getById(1)
        assertNotNull(result)
        assertEquals(cliente, result)
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
        val cliente = Cliente(idCliente = 1)
        `when`(repository.save(cliente)).thenReturn(cliente)

        val result = service.save(cliente)
        assertEquals(cliente, result)
        verify(repository).save(cliente)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.deleteById(1)
        verify(repository).deleteById(1)
    }

    @Test
    fun testFindByDniFound() {
        val cliente = Cliente(dni = "12345")
        `when`(repository.findByDni("12345")).thenReturn(cliente)

        val result = service.findByDni("12345")
        assertNotNull(result)
        assertEquals(cliente, result)
        verify(repository).findByDni("12345")
    }

    @Test
    fun testFindByDniNotFound() {
        `when`(repository.findByDni("12345")).thenReturn(null)

        val result = service.findByDni("12345")
        assertNull(result)
        verify(repository).findByDni("12345")
    }
}
