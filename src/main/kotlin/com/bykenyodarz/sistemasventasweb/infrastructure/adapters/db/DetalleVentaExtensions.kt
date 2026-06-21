package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.DetalleVenta

fun DetalleVentaData.toDomain(): DetalleVenta {
    return DetalleVenta(
        idDetalleVenta = this.idDetalleVenta,
        venta = this.venta?.toDomain(),
        producto = this.producto?.toDomain(),
        cantidad = this.cantidad,
        precioVenta = this.precioVenta
    )
}

fun DetalleVenta.toData(): DetalleVentaData {
    val data = DetalleVentaData()
    data.idDetalleVenta = this.idDetalleVenta
    data.venta = this.venta?.toData()
    data.producto = this.producto?.toData()
    data.cantidad = this.cantidad
    data.precioVenta = this.precioVenta
    return data
}
