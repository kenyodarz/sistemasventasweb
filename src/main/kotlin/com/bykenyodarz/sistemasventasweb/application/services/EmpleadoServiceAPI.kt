package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface EmpleadoServiceAPI : GenericServiceAPI<Empleado, Int> {
    fun findEmpleadoByUserAndDni(user: String, dni: String): Empleado?
}
