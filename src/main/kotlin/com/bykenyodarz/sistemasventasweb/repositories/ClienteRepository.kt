package com.bykenyodarz.sistemasventasweb.repositories

import com.bykenyodarz.sistemasventasweb.models.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClienteRepository : JpaRepository<Cliente, Int> {

    @Query("select c from Cliente c where c.dni =?1")
    fun findByDni(dni: String): Optional<Cliente>
}
