package com.bykenyodarz.sistemasventasweb.models

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "cliente",
    uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("Dni"))])
class Cliente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IdCliente")
    var idCliente: Int? = null

    @Column(name = "Dni", unique = true)
    @NotBlank
    @Size(min=1, max=10)
    var dni: String? = null

    @Column(name = "Nombres")
    @NotBlank
    var nombres: String? = null

    @Column(name = "Direccion")
    var direccion: String? = null

    @Column(name = "Estado")
    @Size(min=1,max=1)
    var estado: String? = null

}
