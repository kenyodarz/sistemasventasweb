package com.bykenyodarz.sistemasventasweb.services.apis

import com.bykenyodarz.sistemasventasweb.models.Empleado
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceAPI
import java.util.*

interface EmpleadoServiceAPI : GenericServiceAPI<Empleado, Int> {
    fun findEmpleadoByUserAndDni(user: String, dni: String): Empleado?
}
