package com.bykenyodarz.sistemasventasweb.domain.ports

import com.bykenyodarz.sistemasventasweb.domain.model.Producto
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface ProductoRepository : GenericServiceAPI<Producto, Int> {
    fun findProductoWithStock(id: Int): Producto?
}
