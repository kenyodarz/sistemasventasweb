package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SpringDataEmpleadoRepository : JpaRepository<EmpleadoData, Int> {

    @Query(value = "select e from EmpleadoData e where e.user = ?1 and e.dni = ?2")
    fun findEmpleadoByUserAndDni(user: String, dni: String): Optional<EmpleadoData>
}
