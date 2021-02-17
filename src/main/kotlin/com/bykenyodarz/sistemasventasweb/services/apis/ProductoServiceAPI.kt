package com.bykenyodarz.sistemasventasweb.services.apis

import com.bykenyodarz.sistemasventasweb.models.Producto
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceAPI
import java.util.*

interface ProductoServiceAPI : GenericServiceAPI<Producto, Int> {
    fun findProductoWithStock(id: Int) : Producto?
}
