package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Cliente
import com.bykenyodarz.sistemasventasweb.repositories.ClienteRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class ClienteServiceImplTest {

    private lateinit var repository: ClienteRepository
    private lateinit var service: ClienteServiceImpl

    @BeforeEach
    fun setUp() {
        repository = mock(ClienteRepository::class.java)
        service = ClienteServiceImpl(repository)
    }

    @Test
    fun testGetRepository() {
        assertEquals(repository, service.getRepository())
    }

    @Test
    fun testGetAll() {
        val cliente = Cliente().apply { idCliente = 1; dni = "12345678" }
        `when`(repository.findAll()).thenReturn(listOf(cliente))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(cliente, result[0])
        verify(repository).findAll()
    }

    @Test
    fun testGetOneFound() {
        val cliente = Cliente().apply { idCliente = 1 }
        `when`(repository.findById(1)).thenReturn(Optional.of(cliente))

        val result = service.getOne(1)
        assertNotNull(result)
        assertEquals(cliente, result)
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
        val cliente = Cliente().apply { idCliente = 1 }
        `when`(repository.save(cliente)).thenReturn(cliente)

        val result = service.save(cliente)
        assertEquals(cliente, result)
        verify(repository).save(cliente)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.delete(1)
        verify(repository).deleteById(1)
    }

    @Test
    fun testFindByDniFound() {
        val cliente = Cliente().apply { dni = "12345" }
        `when`(repository.findByDni("12345")).thenReturn(Optional.of(cliente))

        val result = service.findByDni("12345")
        assertNotNull(result)
        assertEquals(cliente, result)
        verify(repository).findByDni("12345")
    }

    @Test
    fun testFindByDniNotFound() {
        `when`(repository.findByDni("12345")).thenReturn(Optional.empty())

        val result = service.findByDni("12345")
        assertNull(result)
        verify(repository).findByDni("12345")
    }
}
