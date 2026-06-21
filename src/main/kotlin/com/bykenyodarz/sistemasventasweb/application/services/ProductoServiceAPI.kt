package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Producto
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface ProductoServiceAPI : GenericServiceAPI<Producto, Int> {
    fun findProductoWithStock(id: Int): Producto?
}
