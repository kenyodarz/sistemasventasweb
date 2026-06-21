package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.ProductoServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.Producto
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateProductoRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.ProductoResponse
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
        val producto = Producto(idProducto = 1, nombres = "Producto 1", precio = 10.0, stock = 10, estado = "1")
        `when`(service.getAll()).thenReturn(listOf(producto))

        val response = controller.getAll()
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as List<*>
        assertEquals(1, body.size)
        val responseDto = body[0] as ProductoResponse
        assertEquals(1, responseDto.idProducto)
        assertEquals("Producto 1", responseDto.nombres)
        verify(service).getAll()
    }

    @Test
    fun testGetOneFound() {
        val producto = Producto(idProducto = 1, nombres = "Producto 1", precio = 10.0, stock = 10, estado = "1")
        `when`(service.getById(1)).thenReturn(producto)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as ProductoResponse
        assertEquals(1, body.idProducto)
        verify(service).getById(1)
    }

    @Test
    fun testGetOneNotFound() {
        `when`(service.getById(1)).thenReturn(null)

        val response = controller.getOne(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Recurso no encontrado", response.body)
        verify(service).getById(1)
    }

    @Test
    fun testSaveSuccess() {
        val request = CreateProductoRequest(nombres = "Nuevo", precio = 5.0, stock = 5, estado = "1")
        val producto = Producto(idProducto = 1, nombres = "Nuevo", precio = 5.0, stock = 5, estado = "1")
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(false)
        `when`(service.save(anyNonNullProducto())).thenReturn(producto)

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val body = response.body as ProductoResponse
        assertEquals(1, body.idProducto)
        verify(service).save(anyNonNullProducto())
    }

    @Test
    fun testSaveValidationError() {
        val request = CreateProductoRequest()
        val bindingResult = mock(BindingResult::class.java)
        `when`(bindingResult.hasErrors()).thenReturn(true)
        
        val fieldError = FieldError("request", "nombres", "must not be blank")
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val response = controller.save(request, bindingResult)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        @Suppress("UNCHECKED_CAST")
        val body = response.body as Map<String, Any>
        assertEquals("El campo nombres must not be blank", body["nombres"])
        verify(service, never()).save(anyNonNullProducto())
    }

    @Test
    fun testDeleteFound() {
        val producto = Producto(idProducto = 1)
        `when`(service.getById(1)).thenReturn(producto)
        doNothing().`when`(service).deleteById(1)

        val response = controller.delete(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as ProductoResponse
        assertEquals(1, body.idProducto)
        verify(service).getById(1)
        verify(service).deleteById(1)
    }

    @Test
    fun testDeleteNotFound() {
        `when`(service.getById(1)).thenReturn(null)

        val response = controller.delete(1)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(service).getById(1)
        verify(service, never()).deleteById(anyInt())
    }

    @Test
    fun testFindProductoWithStockFound() {
        val producto = Producto(idProducto = 1, stock = 5)
        `when`(service.findProductoWithStock(1)).thenReturn(producto)

        val response = controller.findProductoWithStock(1)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as ProductoResponse
        assertEquals(1, body.idProducto)
        assertEquals(5, body.stock)
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
        val producto = Producto(idProducto = 1, stock = 10)
        `when`(service.getById(1)).thenReturn(producto)
        // El controlador modifica el stock del producto obtenido y lo guarda
        `when`(service.save(anyNonNullProducto())).thenAnswer { invocation ->
            invocation.getArgument<Producto>(0)
        }

        val response = controller.actualizarStock(1, 3)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as ProductoResponse
        assertEquals(7, body.stock)
        verify(service).getById(1)
        verify(service).save(anyNonNullProducto())
    }

    @Test
    fun testActualizarStockFoundWithStockNull() {
        val producto = Producto(idProducto = 1, stock = null)
        `when`(service.getById(1)).thenReturn(producto)
        `when`(service.save(anyNonNullProducto())).thenAnswer { invocation ->
            invocation.getArgument<Producto>(0)
        }

        val response = controller.actualizarStock(1, 3)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body as ProductoResponse
        assertNull(body.stock)
        verify(service).getById(1)
        verify(service).save(anyNonNullProducto())
    }

    @Test
    fun testActualizarStockNotFound() {
        `when`(service.getById(1)).thenReturn(null)

        val response = controller.actualizarStock(1, 3)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Producto inexistente o sin stock", response.body)
        verify(service).getById(1)
        verify(service, never()).save(anyNonNullProducto())
    }

    private fun anyNonNullProducto(): Producto {
        any<Producto>()
        return Producto()
    }
}
