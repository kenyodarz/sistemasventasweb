package com.bykenyodarz.sistemasventasweb.domain.ports

import com.bykenyodarz.sistemasventasweb.domain.model.DetalleVenta
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface DetalleVentaRepository : GenericServiceAPI<DetalleVenta, Int>
