package com.bykenyodarz.sistemasventasweb.services.apis

import com.bykenyodarz.sistemasventasweb.models.Venta
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceAPI
import java.util.*

interface VentaServiceAPI : GenericServiceAPI<Venta, Int> {
    fun findMAxNumeroSerie(): Int
}
