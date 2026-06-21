package com.bykenyodarz.sistemasventasweb.domain.ports

import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface EmpleadoRepository : GenericServiceAPI<Empleado, Int> {
    fun findEmpleadoByUserAndDni(user: String, dni: String): Empleado?
}
