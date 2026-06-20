package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Venta
import com.bykenyodarz.sistemasventasweb.repositories.VentaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class VentaServiceImplTest {

    private lateinit var repository: VentaRepository
    private lateinit var service: VentaServiceImpl

    @BeforeEach
    fun setUp() {
        repository = mock(VentaRepository::class.java)
        service = VentaServiceImpl(repository)
    }

    @Test
    fun testGetRepository() {
        assertEquals(repository, service.getRepository())
    }

    @Test
    fun testGetAll() {
        val venta = Venta().apply { idVenta = 1; numeroSerie = "00000001" }
        `when`(repository.findAll()).thenReturn(listOf(venta))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(venta, result[0])
        verify(repository).findAll()
    }

    @Test
    fun testGetOneFound() {
        val venta = Venta().apply { idVenta = 1 }
        `when`(repository.findById(1)).thenReturn(Optional.of(venta))

        val result = service.getOne(1)
        assertNotNull(result)
        assertEquals(venta, result)
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
        val venta = Venta().apply { idVenta = 1 }
        `when`(repository.save(venta)).thenReturn(venta)

        val result = service.save(venta)
        assertEquals(venta, result)
        verify(repository).save(venta)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.delete(1)
        verify(repository).deleteById(1)
    }

    @Test
    fun testFindMAxNumeroSerieFound() {
        `when`(repository.findMAxNumeroSerie()).thenReturn(Optional.of(5))

        val result = service.findMAxNumeroSerie()
        assertEquals(5, result)
        verify(repository).findMAxNumeroSerie()
    }

    @Test
    fun testFindMAxNumeroSerieNotFound() {
        `when`(repository.findMAxNumeroSerie()).thenReturn(Optional.empty())

        val result = service.findMAxNumeroSerie()
        assertEquals(0, result)
        verify(repository).findMAxNumeroSerie()
    }
}
