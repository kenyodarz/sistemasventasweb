package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.DetalleVenta
import com.bykenyodarz.sistemasventasweb.repositories.DetalleVentaRepository
import com.bykenyodarz.sistemasventasweb.services.apis.DetalleVentaServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class DetalleVentaServiceImpl(repository: DetalleVentaRepository) : GenericServiceImpl<DetalleVenta, Int>(),
    DetalleVentaServiceAPI {

    private val repository: DetalleVentaRepository

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<DetalleVenta, Int> {
        return this.repository
    }
}