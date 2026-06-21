package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.Empleado

fun EmpleadoData.toDomain(): Empleado {
    return Empleado(
        idEmpleado = this.idEmpleado,
        dni = this.dni,
        nombres = this.nombres,
        telefono = this.telefono,
        estado = this.estado,
        user = this.user
    )
}

fun Empleado.toData(): EmpleadoData {
    val data = EmpleadoData()
    data.idEmpleado = this.idEmpleado
    data.dni = this.dni
    data.nombres = this.nombres
    data.telefono = this.telefono
    data.estado = this.estado
    data.user = this.user
    return data
}
