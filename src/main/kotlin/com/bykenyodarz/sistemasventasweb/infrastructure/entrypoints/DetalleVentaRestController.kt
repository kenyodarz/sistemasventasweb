package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.DetalleVentaServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.DetalleVenta
import com.bykenyodarz.sistemasventasweb.domain.model.Venta
import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.domain.model.Producto
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateDetalleVentaRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.DetalleVentaResponse
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.VentaResponse
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.ClienteResponse
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.EmpleadoResponse
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.ProductoResponse
import com.bykenyodarz.sistemasventasweb.shared.entrypoints.AbstractGenericRestController
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/detalle")
@Tag(name = "detalle")
class DetalleVentaRestController(
    private val detalleVentaServiceAPI: DetalleVentaServiceAPI
) : AbstractGenericRestController<DetalleVenta, Int, CreateDetalleVentaRequest, DetalleVentaResponse>(
    detalleVentaServiceAPI,
    { req ->
        DetalleVenta(
            idDetalleVenta = req.idDetalleVenta,
            venta = req.venta?.let { v ->
                Venta(
                    idVenta = v.idVenta,
                    cliente = v.cliente?.let { c ->
                        Cliente(
                            idCliente = c.idCliente,
                            dni = c.dni,
                            nombres = c.nombres,
                            direccion = c.direccion,
                            estado = c.estado
                        )
                    },
                    empleado = v.empleado?.let { e ->
                        Empleado(
                            idEmpleado = e.idEmpleado,
                            dni = e.dni,
                            nombres = e.nombres,
                            telefono = e.telefono,
                            estado = e.estado,
                            user = e.user
                        )
                    },
                    numeroSerie = v.numeroSerie,
                    fechaVentas = v.fechaVentas,
                    monto = v.monto,
                    estado = v.estado
                )
            },
            producto = req.producto?.let { p ->
                Producto(
                    idProducto = p.idProducto,
                    nombres = p.nombres,
                    precio = p.precio,
                    stock = p.stock,
                    estado = p.estado
                )
            },
            cantidad = req.cantidad,
            precioVenta = req.precioVenta
        )
    },
    { entity ->
        DetalleVentaResponse(
            idDetalleVenta = entity.idDetalleVenta,
            venta = entity.venta?.let { v ->
                VentaResponse(
                    idVenta = v.idVenta,
                    cliente = v.cliente?.let { c ->
                        ClienteResponse(
                            idCliente = c.idCliente,
                            dni = c.dni,
                            nombres = c.nombres,
                            direccion = c.direccion,
                            estado = c.estado
                        )
                    },
                    empleado = v.empleado?.let { e ->
                        EmpleadoResponse(
                            idEmpleado = e.idEmpleado,
                            dni = e.dni,
                            nombres = e.nombres,
                            telefono = e.telefono,
                            estado = e.estado,
                            user = e.user
                        )
                    },
                    numeroSerie = v.numeroSerie,
                    fechaVentas = v.fechaVentas,
                    monto = v.monto,
                    estado = v.estado
                )
            },
            producto = entity.producto?.let { p ->
                ProductoResponse(
                    idProducto = p.idProducto,
                    nombres = p.nombres,
                    precio = p.precio,
                    stock = p.stock,
                    estado = p.estado
                )
            },
            cantidad = entity.cantidad,
            precioVenta = entity.precioVenta
        )
    }
)
