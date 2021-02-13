package com.bykenyodarz.sistemasventasweb.services.apis

import com.bykenyodarz.sistemasventasweb.models.Cliente
import com.bykenyodarz.sistemasventasweb.shared.GenericServiceAPI
import java.util.*

interface ClienteServiceAPI : GenericServiceAPI<Cliente, Int>{
    fun findByDni(dni: String): Cliente?
}
