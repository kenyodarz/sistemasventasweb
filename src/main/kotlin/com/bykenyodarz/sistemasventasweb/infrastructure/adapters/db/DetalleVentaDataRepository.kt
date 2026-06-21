package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.DetalleVenta
import com.bykenyodarz.sistemasventasweb.domain.ports.DetalleVentaRepository
import com.bykenyodarz.sistemasventasweb.shared.infrastructure.AbstractAdapterOperations
import org.springframework.stereotype.Repository

@Repository
class DetalleVentaDataRepository(
    repository: SpringDataDetalleVentaRepository
) : AbstractAdapterOperations<DetalleVenta, DetalleVentaData, Int, SpringDataDetalleVentaRepository>(
    repository,
    { data -> data.toDomain() },
    { domain -> domain.toData() }
), DetalleVentaRepository
