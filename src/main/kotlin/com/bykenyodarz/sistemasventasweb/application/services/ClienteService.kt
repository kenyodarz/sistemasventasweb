package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.domain.ports.ClienteRepository
import com.bykenyodarz.sistemasventasweb.shared.application.AbstractCrudServiceOperations
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Service
class ClienteService(
    private val clienteRepository: ClienteRepository
) : AbstractCrudServiceOperations<Cliente, Int>(clienteRepository), ClienteServiceAPI {

    @Transactional
    override fun findByDni(dni: String): Cliente? {
        return clienteRepository.findByDni(dni)
    }
}
