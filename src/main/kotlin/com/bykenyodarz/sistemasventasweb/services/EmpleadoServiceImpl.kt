package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Empleado
import com.bykenyodarz.sistemasventasweb.repositories.EmpleadoRepository
import com.bykenyodarz.sistemasventasweb.services.apis.EmpleadoServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import javax.validation.constraints.NotNull

@Service
class EmpleadoServiceImpl(repository: EmpleadoRepository) : GenericServiceImpl<Empleado, Int>(),
    EmpleadoServiceAPI {

    private val repository: EmpleadoRepository

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<Empleado, Int> {
        return this.repository
    }

    @NotNull
    @Transactional
    override fun findEmpleadoByUserAndDni(user: String, dni: String): Empleado? {
        val empleado: Optional<Empleado> = this.repository.findEmpleadoByUserAndDni(user, dni)
        return empleado.orElse(null)
    }
}
