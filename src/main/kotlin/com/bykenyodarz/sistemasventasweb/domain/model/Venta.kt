package com.bykenyodarz.sistemasventasweb.domain.model

import java.time.LocalDate

data class Venta(
    var idVenta: Int? = null,
    var cliente: Cliente? = null,
    var empleado: Empleado? = null,
    var numeroSerie: String? = null,
    var fechaVentas: LocalDate? = null,
    var monto: Double? = null,
    var estado: String? = null
)
