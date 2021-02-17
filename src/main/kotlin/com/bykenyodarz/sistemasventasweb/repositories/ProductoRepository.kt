package com.bykenyodarz.sistemasventasweb.repositories

import com.bykenyodarz.sistemasventasweb.models.Producto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductoRepository : JpaRepository<Producto, Int> {

    @Query("select p from Producto p where p.stock > 0 and p.idProducto = ?1")
    fun findProductoWithStock(id: Int) : Optional<Producto>

}
