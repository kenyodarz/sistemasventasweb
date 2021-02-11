package com.bykenyodarz.sistemasventasweb.repositories

import com.bykenyodarz.sistemasventasweb.models.Venta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VentaRepository : JpaRepository<Venta, Int>