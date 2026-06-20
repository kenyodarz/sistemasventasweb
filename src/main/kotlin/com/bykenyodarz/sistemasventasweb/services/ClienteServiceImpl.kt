package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Cliente
import com.bykenyodarz.sistemasventasweb.repositories.ClienteRepository
import com.bykenyodarz.sistemasventasweb.services.apis.ClienteServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotNull

/**
 * Implementación del servicio para la gestión de la entidad Cliente.
 * Extiende de [GenericServiceImpl] para heredar el comportamiento CRUD básico
 * e implementa [ClienteServiceAPI] para definir operaciones específicas de negocio.
 *
 * @property repository El repositorio específico de acceso a datos para clientes.
 */
@Service
class ClienteServiceImpl(repository: ClienteRepository) : GenericServiceImpl<Cliente, Int>(), ClienteServiceAPI {

    private val repository: ClienteRepository

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<Cliente, Int> {
        return this.repository
    }

    /**
     * Busca un cliente en el sistema utilizando su número de DNI.
     *
     * @param dni El Documento Nacional de Identidad del cliente.
     * @return El objeto [Cliente] asociado al DNI, o `null` si no se encuentra registrado.
     */
    @NotNull
    @Transactional
    override fun findByDni(dni: String): Cliente? {
        return this.repository.findByDni(dni).orElse(null)
    }
}
