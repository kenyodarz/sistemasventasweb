package com.bykenyodarz.sistemasventasweb.domain.model

data class Cliente(
    var idCliente: Int? = null,
    var dni: String? = null,
    var nombres: String? = null,
    var direccion: String? = null,
    var estado: String? = null
)
