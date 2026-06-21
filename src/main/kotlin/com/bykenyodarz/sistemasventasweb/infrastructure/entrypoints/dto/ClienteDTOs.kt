package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateClienteRequest(
    var idCliente: Int? = null,

    @field:NotBlank
    @field:Size(min = 1, max = 10)
    var dni: String? = null,

    @field:NotBlank
    var nombres: String? = null,

    var direccion: String? = null,

    @field:Size(min = 1, max = 1)
    var estado: String? = null
)

data class ClienteResponse(
    val idCliente: Int?,
    val dni: String?,
    val nombres: String?,
    val direccion: String?,
    val estado: String?
)
