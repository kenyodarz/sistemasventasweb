package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto

import java.time.LocalDate

data class CreateVentaRequest(
    var idVenta: Int? = null,
    var cliente: CreateClienteRequest? = null,
    var empleado: CreateEmpleadoRequest? = null,
    var numeroSerie: String? = null,
    var fechaVentas: LocalDate? = null,
    var monto: Double? = null,
    var estado: String? = null
)

data class VentaResponse(
    val idVenta: Int?,
    val cliente: ClienteResponse?,
    val empleado: EmpleadoResponse?,
    val numeroSerie: String?,
    val fechaVentas: LocalDate?,
    val monto: Double?,
    val estado: String?
)
