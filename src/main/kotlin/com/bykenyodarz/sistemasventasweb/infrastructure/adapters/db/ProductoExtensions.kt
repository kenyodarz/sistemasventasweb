package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.Producto

fun ProductoData.toDomain(): Producto {
    return Producto(
        idProducto = this.idProducto,
        nombres = this.nombres,
        precio = this.precio,
        stock = this.stock,
        estado = this.estado
    )
}

fun Producto.toData(): ProductoData {
    val data = ProductoData()
    data.idProducto = this.idProducto
    data.nombres = this.nombres
    data.precio = this.precio
    data.stock = this.stock
    data.estado = this.estado
    return data
}
