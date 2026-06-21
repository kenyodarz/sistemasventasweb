package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Venta
import com.bykenyodarz.sistemasventasweb.domain.ports.VentaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class VentaServiceTest {

    private lateinit var repository: VentaRepository
    private lateinit var service: VentaService

    @BeforeEach
    fun setUp() {
        repository = mock(VentaRepository::class.java)
        service = VentaService(repository)
    }

    @Test
    fun testGetAll() {
        val venta = Venta(idVenta = 1, numeroSerie = "00000001")
        `when`(repository.getAll()).thenReturn(listOf(venta))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(venta, result[0])
        verify(repository).getAll()
    }

    @Test
    fun testGetByIdFound() {
        val venta = Venta(idVenta = 1)
        `when`(repository.getById(1)).thenReturn(venta)

        val result = service.getById(1)
        assertNotNull(result)
        assertEquals(venta, result)
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
        val venta = Venta(idVenta = 1)
        `when`(repository.save(venta)).thenReturn(venta)

        val result = service.save(venta)
        assertEquals(venta, result)
        verify(repository).save(venta)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.deleteById(1)
        verify(repository).deleteById(1)
    }

    @Test
    fun testFindMaxNumeroSerie() {
        `when`(repository.findMAxNumeroSerie()).thenReturn(15)

        val result = service.findMAxNumeroSerie()
        assertEquals(15, result)
        verify(repository).findMAxNumeroSerie()
    }
}
