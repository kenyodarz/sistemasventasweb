package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.domain.ports.EmpleadoRepository
import com.bykenyodarz.sistemasventasweb.shared.application.AbstractCrudServiceOperations
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Service
class EmpleadoService(
    private val empleadoRepository: EmpleadoRepository
) : AbstractCrudServiceOperations<Empleado, Int>(empleadoRepository), EmpleadoServiceAPI {

    @Transactional
    override fun findEmpleadoByUserAndDni(user: String, dni: String): Empleado? {
        return empleadoRepository.findEmpleadoByUserAndDni(user, dni)
    }
}
