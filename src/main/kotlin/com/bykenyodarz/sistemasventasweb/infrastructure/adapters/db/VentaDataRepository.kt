package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.Venta
import com.bykenyodarz.sistemasventasweb.domain.ports.VentaRepository
import com.bykenyodarz.sistemasventasweb.shared.infrastructure.AbstractAdapterOperations
import org.springframework.stereotype.Repository

@Repository
class VentaDataRepository(
    repository: SpringDataVentaRepository
) : AbstractAdapterOperations<Venta, VentaData, Int, SpringDataVentaRepository>(
    repository,
    { data -> data.toDomain() },
    { domain -> domain.toData() }
), VentaRepository {

    override fun findMAxNumeroSerie(): Int {
        val optional = repository.findMAxNumeroSerie()
        return optional.orElse(0)
    }
}
