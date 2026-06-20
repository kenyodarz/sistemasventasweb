package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Producto
import com.bykenyodarz.sistemasventasweb.repositories.ProductoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class ProductoServiceImplTest {

    private lateinit var repository: ProductoRepository
    private lateinit var service: ProductoServiceImpl

    @BeforeEach
    fun setUp() {
        repository = mock(ProductoRepository::class.java)
        service = ProductoServiceImpl(repository)
    }

    @Test
    fun testGetRepository() {
        assertEquals(repository, service.getRepository())
    }

    @Test
    fun testGetAll() {
        val producto = Producto().apply { idProducto = 1; nombres = "Teclado" }
        `when`(repository.findAll()).thenReturn(listOf(producto))

        val result = service.getAll()
        assertEquals(1, result.size)
        assertEquals(producto, result[0])
        verify(repository).findAll()
    }

    @Test
    fun testGetOneFound() {
        val producto = Producto().apply { idProducto = 1 }
        `when`(repository.findById(1)).thenReturn(Optional.of(producto))

        val result = service.getOne(1)
        assertNotNull(result)
        assertEquals(producto, result)
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
        val producto = Producto().apply { idProducto = 1 }
        `when`(repository.save(producto)).thenReturn(producto)

        val result = service.save(producto)
        assertEquals(producto, result)
        verify(repository).save(producto)
    }

    @Test
    fun testDelete() {
        doNothing().`when`(repository).deleteById(1)

        service.delete(1)
        verify(repository).deleteById(1)
    }

    @Test
    fun testFindProductoWithStockFound() {
        val producto = Producto().apply { idProducto = 1; stock = 10 }
        `when`(repository.findProductoWithStock(1)).thenReturn(Optional.of(producto))

        val result = service.findProductoWithStock(1)
        assertNotNull(result)
        assertEquals(producto, result)
        verify(repository).findProductoWithStock(1)
    }

    @Test
    fun testFindProductoWithStockNotFound() {
        `when`(repository.findProductoWithStock(1)).thenReturn(Optional.empty())

        val result = service.findProductoWithStock(1)
        assertNull(result)
        verify(repository).findProductoWithStock(1)
    }
}
