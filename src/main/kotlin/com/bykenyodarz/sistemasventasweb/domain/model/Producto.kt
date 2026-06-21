package com.bykenyodarz.sistemasventasweb.domain.model

data class Producto(
    var idProducto: Int? = null,
    var nombres: String? = null,
    var precio: Double? = null,
    var stock: Int? = null,
    var estado: String? = null
)
