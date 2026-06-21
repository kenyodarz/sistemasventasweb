package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateProductoRequest(
    var idProducto: Int? = null,

    @field:NotBlank
    var nombres: String? = null,

    @field:NotNull
    var precio: Double? = null,

    @field:NotNull
    var stock: Int? = null,

    @field:NotBlank
    var estado: String? = null
)

data class ProductoResponse(
    val idProducto: Int?,
    val nombres: String?,
    val precio: Double?,
    val stock: Int?,
    val estado: String?
)
