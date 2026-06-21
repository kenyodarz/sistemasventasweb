package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SpringDataClienteRepository : JpaRepository<ClienteData, Int> {

    @Query("select c from ClienteData c where c.dni = ?1")
    fun findByDni(dni: String): Optional<ClienteData>
}
