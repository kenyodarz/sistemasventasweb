package com.bykenyodarz.sistemasventasweb.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ModelsTest {

    @Test
    fun testCliente() {
        val cliente = Cliente()
        cliente.idCliente = 1
        cliente.dni = "123"
        cliente.nombres = "Juan"
        cliente.direccion = "Av 123"
        cliente.estado = "A"

        assertEquals(1, cliente.idCliente)
        assertEquals("123", cliente.dni)
        assertEquals("Juan", cliente.nombres)
        assertEquals("Av 123", cliente.direccion)
        assertEquals("A", cliente.estado)
    }

    @Test
    fun testEmpleado() {
        val empleado = Empleado()
        empleado.idEmpleado = 1
        empleado.dni = "456"
        empleado.nombres = "Maria"
        empleado.telefono = "999"
        empleado.estado = "A"
        empleado.user = "maria1"

        assertEquals(1, empleado.idEmpleado)
        assertEquals("456", empleado.dni)
        assertEquals("Maria", empleado.nombres)
        assertEquals("999", empleado.telefono)
        assertEquals("A", empleado.estado)
        assertEquals("maria1", empleado.user)
    }

    @Test
    fun testProducto() {
        val prod = Producto()
        prod.idProducto = 1
        prod.nombres = "Laptop"
        prod.precio = 1500.0
        prod.stock = 5
        prod.estado = "A"

        assertEquals(1, prod.idProducto)
        assertEquals("Laptop", prod.nombres)
        assertEquals(1500.0, prod.precio)
        assertEquals(5, prod.stock)
        assertEquals("A", prod.estado)
    }

    @Test
    fun testDetalleVenta() {
        val detalle = DetalleVenta()
        val venta = Venta()
        val prod = Producto()
        detalle.idDetalleVenta = 1
        detalle.venta = venta
        detalle.producto = prod
        detalle.cantidad = 2
        detalle.precioVenta = 100.0

        assertEquals(1, detalle.idDetalleVenta)
        assertEquals(venta, detalle.venta)
        assertEquals(prod, detalle.producto)
        assertEquals(2, detalle.cantidad)
        assertEquals(100.0, detalle.precioVenta)
    }

    @Test
    fun testVenta() {
        val venta = Venta()
        val cliente = Cliente()
        val empleado = Empleado()
        venta.idVenta = 1
        venta.cliente = cliente
        venta.empleado = empleado
        venta.numeroSerie = "001"
        venta.monto = 200.0
        venta.estado = "A"

        venta.prePersist()

        assertEquals(1, venta.idVenta)
        assertEquals(cliente, venta.cliente)
        assertEquals(empleado, venta.empleado)
        assertEquals("001", venta.numeroSerie)
        assertEquals(LocalDate.now(), venta.fechaVentas)
        assertEquals(200.0, venta.monto)
        assertEquals("A", venta.estado)
    }
}
