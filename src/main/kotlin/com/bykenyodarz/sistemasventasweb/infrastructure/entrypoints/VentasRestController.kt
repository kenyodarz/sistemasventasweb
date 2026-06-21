package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.VentaServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.Venta
import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateVentaRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.VentaResponse
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.ClienteResponse
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.EmpleadoResponse
import com.bykenyodarz.sistemasventasweb.shared.entrypoints.AbstractGenericRestController
import com.bykenyodarz.sistemasventasweb.util.GenerarSerie
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/ventas")
@Tag(name = "venta")
class VentasRestController(
    private val ventaServiceAPI: VentaServiceAPI
) : AbstractGenericRestController<Venta, Int, CreateVentaRequest, VentaResponse>(
    ventaServiceAPI,
    { req ->
        Venta(
            idVenta = req.idVenta,
            cliente = req.cliente?.let {
                Cliente(
                    idCliente = it.idCliente,
                    dni = it.dni,
                    nombres = it.nombres,
                    direccion = it.direccion,
                    estado = it.estado
                )
            },
            empleado = req.empleado?.let {
                Empleado(
                    idEmpleado = it.idEmpleado,
                    dni = it.dni,
                    nombres = it.nombres,
                    telefono = it.telefono,
                    estado = it.estado,
                    user = it.user
                )
            },
            numeroSerie = req.numeroSerie,
            fechaVentas = req.fechaVentas,
            monto = req.monto,
            estado = req.estado
        )
    },
    { entity ->
        VentaResponse(
            idVenta = entity.idVenta,
            cliente = entity.cliente?.let {
                ClienteResponse(
                    idCliente = it.idCliente,
                    dni = it.dni,
                    nombres = it.nombres,
                    direccion = it.direccion,
                    estado = it.estado
                )
            },
            empleado = entity.empleado?.let {
                EmpleadoResponse(
                    idEmpleado = it.idEmpleado,
                    dni = it.dni,
                    nombres = it.nombres,
                    telefono = it.telefono,
                    estado = it.estado,
                    user = it.user
                )
            },
            numeroSerie = entity.numeroSerie,
            fechaVentas = entity.fechaVentas,
            monto = entity.monto,
            estado = entity.estado
        )
    }
) {

    @GetMapping("/serie")
    fun obtenerNumeroSerie(): ResponseEntity<GenerarSerie> {
        val generarSerie = GenerarSerie()
        generarSerie.numeroSerie(ventaServiceAPI.findMAxNumeroSerie())
        return ResponseEntity.ok().body(generarSerie)
    }
}
