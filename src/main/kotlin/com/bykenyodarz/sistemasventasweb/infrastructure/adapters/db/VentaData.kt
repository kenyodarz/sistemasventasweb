package com.bykenyodarz.sistemasventasweb.infrastructure.adapters.db

import java.time.LocalDate
import jakarta.persistence.*

@Entity
@Table(name = "ventas")
class VentaData {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IdVentas")
    var idVenta: Int? = null

    @OneToOne
    @JoinColumn(name = "IdCliente")
    var cliente: ClienteData? = null

    @OneToOne
    @JoinColumn(name = "IdEmpleado")
    var empleado: EmpleadoData? = null

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
