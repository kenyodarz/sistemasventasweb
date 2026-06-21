package com.bykenyodarz.sistemasventasweb.application.services

import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

interface ClienteServiceAPI : GenericServiceAPI<Cliente, Int> {
    fun findByDni(dni: String): Cliente?
}
