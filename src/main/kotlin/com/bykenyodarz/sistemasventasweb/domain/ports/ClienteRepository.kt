package com.bykenyodarz.sistemasventasweb.domain.ports

import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface ClienteRepository : GenericServiceAPI<Cliente, Int> {
    fun findByDni(dni: String): Cliente?
}
