package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Producto
import com.bykenyodarz.sistemasventasweb.services.apis.ProductoServiceAPI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

class ProductoRestControllerTest {

    private lateinit var service: ProductoServiceAPI
    private lateinit var controller: ProductoRestController

    @BeforeEach
    fun setUp() {
        service = mock(ProductoServiceAPI::class.java)
        controller = ProductoRestController(service)
    }

    @Test
    fun testGetAll() {
        val producto = Producto().apply { idProducto = 1 }
        `when`(service.getAll()).thenReturn(listOf(producto))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(producto), response.body)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val producto = Producto().apply { idProducto = 1 }
        `when`(service.getOne(1)).thenReturn(producto)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(producto, response.body)
        verify(service).getOne(1)
    }

    @Test
    fun testGetOneNotFound() {
        `when`(service.getOne(1)).thenReturn(null)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Recurso no encontrado", response.body)
        verify(service).getOne(1)
    }

    @Test
    fun testSaveSuccess() {
        val producto = Producto().apply { idProducto = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(producto)).thenReturn(producto)

        val response = controller.save(producto, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(producto, response.body)
        verify(service).save(producto)
    }

    @Test
    fun testSaveValidationError() {
        val producto = Producto().apply { idProducto = 1 }
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("producto", "nombres", "must not be blank")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(producto, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo nombres must not be blank", body["nombres"])
        verify(service, never()).save(anyNonNullProducto())
    }

    @Test
    fun testDeleteFound() {
        val producto = Producto().apply { idProducto = 1 }
        `when`(service.getOne(1)).thenReturn(producto)
        doNothing().`when`(service).delete(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(producto, response.body)
        verify(service).getOne(1)
        verify(service).delete(1)
    }

    @Test
    fun testDeleteNotFound() {
        `when`(service.getOne(1)).thenReturn(null)

        val response = controller.delete(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(service).getOne(1)
        verify(service, never()).delete(anyInt())
    }

    @Test
    fun testFindProductoWithStockFound() {
        val producto = Producto().apply { idProducto = 1; stock = 5 }
        `when`(service.findProductoWithStock(1)).thenReturn(producto)

        val response = controller.findProductoWithStock(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(producto, response.body)
        verify(service).findProductoWithStock(1)
    }

    @Test
    fun testFindProductoWithStockNotFound() {
        `when`(service.findProductoWithStock(1)).thenReturn(null)

        val response = controller.findProductoWithStock(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Producto inexistente o sin stock", response.body)
        verify(service).findProductoWithStock(1)
    }

    @Test
    fun testActualizarStockFoundWithStockValued() {
        val producto = Producto().apply { idProducto = 1; stock = 10 }
        `when`(service.getOne(1)).thenReturn(producto)
        `when`(service.save(producto)).thenReturn(producto)

        val response = controller.actualizarStock(1, 3)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(producto, response.body)
        assertEquals(7, producto.stock)
        verify(service).getOne(1)
        verify(service).save(producto)
    }

    @Test
    fun testActualizarStockFoundWithStockNull() {
        val producto = Producto().apply { idProducto = 1; stock = null }
        `when`(service.getOne(1)).thenReturn(producto)
        `when`(service.save(producto)).thenReturn(producto)

        val response = controller.actualizarStock(1, 3)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(producto, response.body)
        assertNull(producto.stock)
        verify(service).getOne(1)
        verify(service).save(producto)
    }

    @Test
    fun testActualizarStockNotFound() {
        `when`(service.getOne(1)).thenReturn(null)

        val response = controller.actualizarStock(1, 3)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Producto inexistente o sin stock", response.body)
        verify(service).getOne(1)
        verify(service, never()).save(anyNonNullProducto())
    }

    private fun anyNonNullProducto(): Producto {
        any<Producto>()
        return Producto()
    }
}
