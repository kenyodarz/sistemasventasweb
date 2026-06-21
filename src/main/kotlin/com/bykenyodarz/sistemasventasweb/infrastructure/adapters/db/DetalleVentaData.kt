package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import jakarta.persistence.*

@Entity
@Table(name = "detalle_ventas")
class DetalleVentaData {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IdDetalleVentas")
    var idDetalleVenta: Int? = null

    @OneToOne
    @JoinColumn(name = "IdVentas")
    var venta: VentaData? = null

    @OneToOne
    @JoinColumn(name = "IdProducto")
    var producto: ProductoData? = null

    @Column(name = "Cantidad")
    var cantidad: Int? = null

    @Column(name = "PrecioVenta")
    var precioVenta: Double? = null
}
