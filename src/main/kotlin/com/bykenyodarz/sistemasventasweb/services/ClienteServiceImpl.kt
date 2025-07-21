package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Cliente
import com.bykenyodarz.sistemasventasweb.repositories.ClienteRepository
import com.bykenyodarz.sistemasventasweb.services.apis.ClienteServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotNull

@Service
class ClienteServiceImpl(repository: ClienteRepository) : GenericServiceImpl<Cliente, Int>(), ClienteServiceAPI {

    private val repository: ClienteRepository

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<Cliente, Int> {
        return this.repository
    }

    @NotNull
    @Transactional
    override fun findByDni(dni: String): Cliente? {
        return this.repository.findByDni(dni).orElse(null)
    }
}
