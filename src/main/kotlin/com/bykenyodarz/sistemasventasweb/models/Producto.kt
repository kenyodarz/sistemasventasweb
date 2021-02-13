package com.bykenyodarz.sistemasventasweb.models

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "producto")
class Producto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IdProducto")
    var idProducto: Int? = null

    @Column(name = "Nombres")
    @NotBlank
    var nombres: String? = null

    @Column(name = "Precio")
    @NotNull
    var precio: Double? = null

    @Column(name = "Stock")
    @NotNull
    var stock: Int? = null

    @Column(name = "Estado")
    @NotBlank
    var estado: String? = null

}
