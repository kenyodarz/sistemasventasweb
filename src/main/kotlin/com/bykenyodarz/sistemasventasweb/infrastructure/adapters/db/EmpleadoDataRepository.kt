package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.domain.ports.EmpleadoRepository
import com.bykenyodarz.sistemasventasweb.shared.infrastructure.AbstractAdapterOperations
import org.springframework.stereotype.Repository

@Repository
class EmpleadoDataRepository(
    repository: SpringDataEmpleadoRepository
) : AbstractAdapterOperations<Empleado, EmpleadoData, Int, SpringDataEmpleadoRepository>(
    repository,
    { data -> data.toDomain() },
    { domain -> domain.toData() }
), EmpleadoRepository {

    override fun findEmpleadoByUserAndDni(user: String, dni: String): Empleado? {
        val optional = repository.findEmpleadoByUserAndDni(user, dni)
        return optional.map { data -> data.toDomain() }.orElse(null)
    }
}
