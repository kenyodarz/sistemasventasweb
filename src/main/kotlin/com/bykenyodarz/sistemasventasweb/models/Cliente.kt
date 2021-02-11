package com.bykenyodarz.sistemasventasweb.models

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "cliente")
class Cliente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IdCliente")
    var idCliente: Int? = null

    @Column(name = "Dni")
    @NotBlank
    var dni: String? = null

    @Column(name = "Nombres")
    @NotBlank
    var nombres: String? = null

    @Column(name = "Direccion")
    var direccion: String? = null

    @Column(name = "Estado")
    var estado: String? = null

}