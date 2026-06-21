package com.bykenyodarz.sistemasventasweb.domain.model

data class DetalleVenta(
    var idDetalleVenta: Int? = null,
    var venta: Venta? = null,
    var producto: Producto? = null,
    var cantidad: Int? = null,
    var precioVenta: Double? = null
)
