package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Producto
import com.bykenyodarz.sistemasventasweb.domain.ports.ProductoRepository
import com.bykenyodarz.sistemasventasweb.shared.application.AbstractCrudServiceOperations
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Service
class ProductoService(
    private val productoRepository: ProductoRepository
) : AbstractCrudServiceOperations<Producto, Int>(productoRepository), ProductoServiceAPI {

    @Transactional
    override fun findProductoWithStock(id: Int): Producto? {
        return productoRepository.findProductoWithStock(id)
    }
}
