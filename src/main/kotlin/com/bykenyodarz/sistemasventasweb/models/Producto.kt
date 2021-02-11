package com.bykenyodarz.sistemasventasweb.models

import javax.persistence.*
import javax.validation.constraints.NotBlank

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
    @NotBlank
    var precio: String? = null

    @Column(name = "Stock")
    @NotBlank
    var stock: String? = null

    @Column(name = "Estado")
    @NotBlank
    var estado: String? = null

}