package com.bykenyodarz.sistemasventasweb.repositories

import com.bykenyodarz.sistemasventasweb.models.DetalleVenta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetalleVentaRepository : JpaRepository<DetalleVenta, Int>