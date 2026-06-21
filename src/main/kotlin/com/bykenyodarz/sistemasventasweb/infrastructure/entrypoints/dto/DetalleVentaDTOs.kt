package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto

data class CreateDetalleVentaRequest(
    var idDetalleVenta: Int? = null,
    var venta: CreateVentaRequest? = null,
    var producto: CreateProductoRequest? = null,
    var cantidad: Int? = null,
    var precioVenta: Double? = null
)

data class DetalleVentaResponse(
    val idDetalleVenta: Int?,
    val venta: VentaResponse?,
    val producto: ProductoResponse?,
    val cantidad: Int?,
    val precioVenta: Double?
)
