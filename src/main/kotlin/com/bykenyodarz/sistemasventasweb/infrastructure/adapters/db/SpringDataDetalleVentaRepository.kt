package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataDetalleVentaRepository : JpaRepository<DetalleVentaData, Int>
