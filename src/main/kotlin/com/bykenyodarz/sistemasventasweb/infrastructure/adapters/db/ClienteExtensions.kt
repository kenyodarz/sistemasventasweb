package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.Cliente

fun ClienteData.toDomain(): Cliente {
    return Cliente(
        idCliente = this.idCliente,
        dni = this.dni,
        nombres = this.nombres,
        direccion = this.direccion,
        estado = this.estado
    )
}

fun Cliente.toData(): ClienteData {
    val data = ClienteData()
    data.idCliente = this.idCliente
    data.dni = this.dni
    data.nombres = this.nombres
    data.direccion = this.direccion
    data.estado = this.estado
    return data
}
