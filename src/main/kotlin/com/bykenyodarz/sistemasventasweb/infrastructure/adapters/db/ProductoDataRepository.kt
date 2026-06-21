package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import com.bykenyodarz.sistemasventasweb.domain.model.Producto
import com.bykenyodarz.sistemasventasweb.domain.ports.ProductoRepository
import com.bykenyodarz.sistemasventasweb.shared.infrastructure.AbstractAdapterOperations
import org.springframework.stereotype.Repository

@Repository
class ProductoDataRepository(
    repository: SpringDataProductoRepository
) : AbstractAdapterOperations<Producto, ProductoData, Int, SpringDataProductoRepository>(
    repository,
    { data -> data.toDomain() },
    { domain -> domain.toData() }
), ProductoRepository {

    override fun findProductoWithStock(id: Int): Producto? {
        val optional = repository.findProductoWithStock(id)
        return optional.map { data -> data.toDomain() }.orElse(null)
    }
}
