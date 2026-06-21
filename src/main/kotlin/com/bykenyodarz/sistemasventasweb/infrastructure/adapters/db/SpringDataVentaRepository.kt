package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SpringDataVentaRepository : JpaRepository<VentaData, Int> {

    @Query(value = "select max(v.numeroSerie) from VentaData v")
    fun findMAxNumeroSerie(): Optional<Int>
}
