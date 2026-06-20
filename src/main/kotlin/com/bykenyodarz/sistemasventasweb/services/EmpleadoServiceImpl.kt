package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Empleado
import com.bykenyodarz.sistemasventasweb.repositories.EmpleadoRepository
import com.bykenyodarz.sistemasventasweb.services.apis.EmpleadoServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.util.*
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotNull

/**
 * Implementación del servicio para la gestión de la entidad Empleado.
 * Extiende de [GenericServiceImpl] para heredar el comportamiento CRUD básico
 * e implementa [EmpleadoServiceAPI] para definir operaciones específicas de negocio.
 *
 * @property repository El repositorio específico de acceso a datos para empleados.
 */
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

    /**
     * Busca y valida un empleado en base a su nombre de usuario (user) y número de DNI.
     * Es utilizado usualmente en el flujo de inicio de sesión o validación de credenciales.
     *
     * @param user El nombre de usuario único del empleado.
     * @param dni El Documento Nacional de Identidad del empleado.
     * @return El objeto [Empleado] si coinciden las credenciales, o `null` si no existe.
     */
    @NotNull
    @Transactional
    override fun findEmpleadoByUserAndDni(user: String, dni: String): Empleado? {
        val empleado: Optional<Empleado> = this.repository.findEmpleadoByUserAndDni(user, dni)
        return empleado.orElse(null)
    }
}
