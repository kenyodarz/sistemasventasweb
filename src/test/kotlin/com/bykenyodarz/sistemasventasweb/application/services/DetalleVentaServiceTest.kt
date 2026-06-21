package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.DetalleVenta
import com.bykenyodarz.sistemasventasweb.domain.ports.DetalleVentaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class DetalleVentaServiceTest {

    private lateinit var repository: DetalleVentaRepository
    private lateinit var service: DetalleVentaService

    @BeforeEach
    fun setUp() {
        repository = mock(DetalleVentaRepository::class.java)
        service = DetalleVentaService(repository)
    }

    @Test
    fun testGetAll() {
        val detalle = DetalleVenta(idDetalleVenta = 1, cantidad = 5)
        `when`(repository.getAll()).thenReturn(listOf(detalle))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(detalle, result[0])
        verify(repository).getAll()
    }

    @Test
    fun testGetByIdFound() {
        val detalle = DetalleVenta(idDetalleVenta = 1)
        `when`(repository.getById(1)).thenReturn(detalle)

        val result = service.getById(1)
        assertNotNull(result)
        assertEquals(detalle, result)
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
        val detalle = DetalleVenta(idDetalleVenta = 1)
        `when`(repository.save(detalle)).thenReturn(detalle)

        val result = service.save(detalle)
        assertEquals(detalle, result)
        verify(repository).save(detalle)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.deleteById(1)
        verify(repository).deleteById(1)
    }
}
