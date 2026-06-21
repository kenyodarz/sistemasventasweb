package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto

import jakarta.validation.constraints.NotBlank

data class CreateEmpleadoRequest(
    var idEmpleado: Int? = null,

    @field:NotBlank
    var dni: String? = null,

    @field:NotBlank
    var nombres: String? = null,

    var telefono: String? = null,

    var estado: String? = null,

    var user: String? = null
)

data class EmpleadoResponse(
    val idEmpleado: Int?,
    val dni: String?,
    val nombres: String?,
    val telefono: String?,
    val estado: String?,
    val user: String?
)
