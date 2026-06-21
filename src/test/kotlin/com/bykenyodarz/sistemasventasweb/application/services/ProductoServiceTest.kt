package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Producto
import com.bykenyodarz.sistemasventasweb.domain.ports.ProductoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class ProductoServiceTest {

    private lateinit var repository: ProductoRepository
    private lateinit var service: ProductoService

    @BeforeEach
    fun setUp() {
        repository = mock(ProductoRepository::class.java)
        service = ProductoService(repository)
    }

    @Test
    fun testGetAll() {
        val producto = Producto(idProducto = 1, nombres = "Teclado")
        `when`(repository.getAll()).thenReturn(listOf(producto))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(producto, result[0])
        verify(repository).getAll()
    }

    @Test
    fun testGetByIdFound() {
        val producto = Producto(idProducto = 1)
        `when`(repository.getById(1)).thenReturn(producto)

        val result = service.getById(1)
        assertNotNull(result)
        assertEquals(producto, result)
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
        val producto = Producto(idProducto = 1)
        `when`(repository.save(producto)).thenReturn(producto)

        val result = service.save(producto)
        assertEquals(producto, result)
        verify(repository).save(producto)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.deleteById(1)
        verify(repository).deleteById(1)
    }

    @Test
    fun testFindProductoWithStockFound() {
        val producto = Producto(idProducto = 1, stock = 10)
        `when`(repository.findProductoWithStock(1)).thenReturn(producto)

        val result = service.findProductoWithStock(1)
        assertNotNull(result)
        assertEquals(producto, result)
        verify(repository).findProductoWithStock(1)
    }

    @Test
    fun testFindProductoWithStockNotFound() {
        `when`(repository.findProductoWithStock(1)).thenReturn(null)

        val result = service.findProductoWithStock(1)
        assertNull(result)
        verify(repository).findProductoWithStock(1)
    }
}
