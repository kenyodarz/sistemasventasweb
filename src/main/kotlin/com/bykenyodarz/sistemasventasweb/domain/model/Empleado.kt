package com.bykenyodarz.sistemasventasweb.domain.model

data class Empleado(
    var idEmpleado: Int? = null,
    var dni: String? = null,
    var nombres: String? = null,
    var telefono: String? = null,
    var estado: String? = null,
    var user: String? = null
)
