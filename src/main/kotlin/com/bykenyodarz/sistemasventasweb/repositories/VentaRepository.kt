package com.bykenyodarz.sistemasventasweb.repositories

import com.bykenyodarz.sistemasventasweb.models.Venta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VentaRepository : JpaRepository<Venta, Int> {

    @Query(value = "select max(v.numeroSerie) from Venta v")
    fun findMAxNumeroSerie(): Optional<Int>

}
