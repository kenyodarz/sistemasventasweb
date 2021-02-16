package com.bykenyodarz.sistemasventasweb.models

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "ventas")
class Venta {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IdVentas")
    var idVenta: Int? = null

    @OneToOne
    @JoinColumn(name = "IdCliente")
    var cliente: Cliente? = null

    @OneToOne
    @JoinColumn(name = "IdEmpleado")
    var empleado: Empleado? = null

    @Column(name = "NumeroSerie")
    var numeroSerie: String? = null

    @Column(name = "FechaVentas")
    var fechaVentas: LocalDate? = null

    @Column(name = "Monto")
    var monto: Double? = null

    @Column(name = "Estado")
    var estado: String? = null

    @PrePersist
    fun prePersist() {
        this.fechaVentas = LocalDate.now()
    }

}
