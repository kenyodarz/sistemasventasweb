package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.Venta

fun VentaData.toDomain(): Venta {
    return Venta(
        idVenta = this.idVenta,
        cliente = this.cliente?.toDomain(),
        empleado = this.empleado?.toDomain(),
        numeroSerie = this.numeroSerie,
        fechaVentas = this.fechaVentas,
        monto = this.monto,
        estado = this.estado
    )
}

fun Venta.toData(): VentaData {
    val data = VentaData()
    data.idVenta = this.idVenta
    data.cliente = this.cliente?.toData()
    data.empleado = this.empleado?.toData()
    data.numeroSerie = this.numeroSerie
    data.fechaVentas = this.fechaVentas
    data.monto = this.monto
    data.estado = this.estado
    return data
}
