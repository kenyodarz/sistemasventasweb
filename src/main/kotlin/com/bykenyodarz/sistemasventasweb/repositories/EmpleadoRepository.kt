package com.bykenyodarz.sistemasventasweb.repositories

import com.bykenyodarz.sistemasventasweb.models.Empleado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmpleadoRepository : JpaRepository<Empleado, Int> {
    @Query(value = "select e from Empleado e where e.user=?1 and e.dni=?2")
    fun findEmpleadoByUserAndDni(user: String, dni: String): Optional<Empleado>
}
