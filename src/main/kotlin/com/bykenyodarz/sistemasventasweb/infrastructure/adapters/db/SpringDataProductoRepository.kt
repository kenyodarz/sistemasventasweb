package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SpringDataProductoRepository : JpaRepository<ProductoData, Int> {

    @Query("select p from ProductoData p where p.stock > 0 and p.idProducto = ?1")
    fun findProductoWithStock(id: Int): Optional<ProductoData>
}
