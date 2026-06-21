package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Venta
import com.bykenyodarz.sistemasventasweb.domain.ports.VentaRepository
import com.bykenyodarz.sistemasventasweb.shared.application.AbstractCrudServiceOperations
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Service
class VentaService(
    private val ventaRepository: VentaRepository
) : AbstractCrudServiceOperations<Venta, Int>(ventaRepository), VentaServiceAPI {

    @Transactional
    override fun findMAxNumeroSerie(): Int {
        return ventaRepository.findMAxNumeroSerie()
    }
}
