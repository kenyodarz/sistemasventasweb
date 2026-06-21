package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Venta
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface VentaServiceAPI : GenericServiceAPI<Venta, Int> {
    fun findMAxNumeroSerie(): Int
}
