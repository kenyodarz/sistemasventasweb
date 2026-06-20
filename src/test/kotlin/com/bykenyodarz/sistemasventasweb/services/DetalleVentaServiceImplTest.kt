package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.DetalleVenta
import com.bykenyodarz.sistemasventasweb.repositories.DetalleVentaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class DetalleVentaServiceImplTest {

    private lateinit var repository: DetalleVentaRepository
    private lateinit var service: DetalleVentaServiceImpl

    @BeforeEach
    fun setUp() {
        repository = mock(DetalleVentaRepository::class.java)
        service = DetalleVentaServiceImpl(repository)
    }

    @Test
    fun testGetRepository() {
        assertEquals(repository, service.getRepository())
    }

    @Test
    fun testGetAll() {
        val detalle = DetalleVenta().apply { idDetalleVenta = 1 }
        `when`(repository.findAll()).thenReturn(listOf(detalle))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(detalle, result[0])
        verify(repository).findAll()
    }

    @Test
    fun testGetOneFound() {
        val detalle = DetalleVenta().apply { idDetalleVenta = 1 }
        `when`(repository.findById(1)).thenReturn(Optional.of(detalle))

        val result = service.getOne(1)
        assertNotNull(result)
        assertEquals(detalle, result)
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
        val detalle = DetalleVenta().apply { idDetalleVenta = 1 }
        `when`(repository.save(detalle)).thenReturn(detalle)

        val result = service.save(detalle)
        assertEquals(detalle, result)
        verify(repository).save(detalle)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.delete(1)
        verify(repository).deleteById(1)
    }
}
