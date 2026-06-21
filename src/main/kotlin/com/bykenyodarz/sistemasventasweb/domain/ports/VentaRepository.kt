package com.bykenyodarz.sistemasventasweb.domain.ports

import com.bykenyodarz.sistemasventasweb.domain.model.Venta
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface VentaRepository : GenericServiceAPI<Venta, Int> {
    fun findMAxNumeroSerie(): Int
}
