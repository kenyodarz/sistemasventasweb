package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "producto")
class ProductoData {

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
