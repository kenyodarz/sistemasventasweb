package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.domain.ports.ClienteRepository
import com.bykenyodarz.sistemasventasweb.shared.infrastructure.AbstractAdapterOperations
import org.springframework.stereotype.Repository

@Repository
class ClienteDataRepository(
    repository: SpringDataClienteRepository
) : AbstractAdapterOperations<Cliente, ClienteData, Int, SpringDataClienteRepository>(
    repository,
    { data -> data.toDomain() },
    { domain -> domain.toData() }
), ClienteRepository {

    override fun findByDni(dni: String): Cliente? {
        val optional = repository.findByDni(dni)
        return optional.map { data -> data.toDomain() }.orElse(null)
    }
}
