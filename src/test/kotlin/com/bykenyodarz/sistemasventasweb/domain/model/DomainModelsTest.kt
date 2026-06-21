package com.bykenyodarz.sistemasventasweb.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DomainModelsTest {

    @Test
    fun testClienteModel() {
        val cliente = Cliente(1, "123", "Nombres", "Direccion", "1")
        assertEquals(1, cliente.idCliente)
        assertEquals("123", cliente.dni)
        assertEquals("Nombres", cliente.nombres)
        assertEquals("Direccion", cliente.direccion)
        assertEquals("1", cliente.estado)
    }

    @Test
    fun testProductoModel() {
        val producto = Producto(1, "Nombres", 10.0, 5, "1")
        assertEquals(1, producto.idProducto)
        assertEquals("Nombres", producto.nombres)
        assertEquals(10.0, producto.precio)
        assertEquals(5, producto.stock)
        assertEquals("1", producto.estado)
    }

    @Test
    fun testEmpleadoModel() {
        val empleado = Empleado(1, "123", "Nombres", "123456", "1", "user")
        assertEquals(1, empleado.idEmpleado)
        assertEquals("123", empleado.dni)
        assertEquals("Nombres", empleado.nombres)
        assertEquals("123456", empleado.telefono)
        assertEquals("1", empleado.estado)
        assertEquals("user", empleado.user)
    }

    @Test
    fun testVentaModel() {
        val cliente = Cliente(1)
        val empleado = Empleado(1)
        val date = LocalDate.now()
        val venta = Venta(1, cliente, empleado, "001", date, 50.0, "1")
        assertEquals(1, venta.idVenta)
        assertEquals(cliente, venta.cliente)
        assertEquals(empleado, venta.empleado)
        assertEquals("001", venta.numeroSerie)
        assertEquals(date, venta.fechaVentas)
        assertEquals(50.0, venta.monto)
        assertEquals("1", venta.estado)
    }

    @Test
    fun testDetalleVentaModel() {
        val venta = Venta(1)
        val producto = Producto(1)
        val detalle = DetalleVenta(1, venta, producto, 5, 25.0)
        assertEquals(1, detalle.idDetalleVenta)
        assertEquals(venta, detalle.venta)
        assertEquals(producto, detalle.producto)
        assertEquals(5, detalle.cantidad)
        assertEquals(25.0, detalle.precioVenta)
    }
}
