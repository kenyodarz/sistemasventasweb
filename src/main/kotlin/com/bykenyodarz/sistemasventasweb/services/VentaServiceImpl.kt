package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Venta
import com.bykenyodarz.sistemasventasweb.repositories.VentaRepository
import com.bykenyodarz.sistemasventasweb.services.apis.VentaServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class VentaServiceImpl(repository: VentaRepository) : GenericServiceImpl<Venta, Int>(), VentaServiceAPI {

    private val repository: VentaRepository

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<Venta, Int> {
        return this.repository
    }

    override fun findMAxNumeroSerie(): Int {
        val numeroSerie = this.repository.findMAxNumeroSerie()
        return  numeroSerie.orElse(0)
    }
}
