package com.bykenyodarz.sistemasventasweb.models

import jakarta.persistence.*

@Entity
@Table(name = "detalle_ventas")
class DetalleVenta {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IdDetalleVentas")
    var idDetalleVenta: Int? = null

    @OneToOne
    @JoinColumn(name = "IdVentas")
    var venta: Venta? = null

    @OneToOne
    @JoinColumn(name = "IdProducto")
    var producto: Producto? = null

    @Column(name = "Cantidad")
    var cantidad: Int? = null

    @Column(name = "PrecioVenta")
    var precioVenta: Double? = null

}