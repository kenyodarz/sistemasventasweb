package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.DetalleVenta
import com.bykenyodarz.sistemasventasweb.domain.ports.DetalleVentaRepository
import com.bykenyodarz.sistemasventasweb.shared.application.AbstractCrudServiceOperations
import org.springframework.stereotype.Service

@Service
class DetalleVentaService(
    private val detalleVentaRepository: DetalleVentaRepository
) : AbstractCrudServiceOperations<DetalleVenta, Int>(detalleVentaRepository), DetalleVentaServiceAPI
