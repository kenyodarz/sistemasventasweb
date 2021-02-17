package com.bykenyodarz.sistemasventasweb.services

import com.bykenyodarz.sistemasventasweb.models.Producto
import com.bykenyodarz.sistemasventasweb.repositories.ProductoRepository
import com.bykenyodarz.sistemasventasweb.services.apis.ProductoServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductoServiceImpl(repository: ProductoRepository) : GenericServiceImpl<Producto, Int>(), ProductoServiceAPI {

    private val repository: ProductoRepository

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<Producto, Int> {
        return this.repository
    }

    override fun findProductoWithStock(id: Int): Producto? {
        val producto: Optional<Producto> = this.repository.findProductoWithStock(id)
        return producto.orElse(null)
    }
}
