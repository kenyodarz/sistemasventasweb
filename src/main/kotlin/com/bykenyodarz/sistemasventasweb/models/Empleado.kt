package com.bykenyodarz.sistemasventasweb.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "empleado")
class Empleado {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IdEmpleado")
    var idEmpleado: Int?=null

    @Column(name = "Dni")
    @NotBlank
    var dni: String?=null

    @Column(name = "Nombres")
    @NotBlank
    var nombres: String?=null

    @Column(name = "Telefono")
    var telefono: String?=null

    @Column(name = "Estado")
    var estado: String? = null

    @Column(name = "User")
    var user: String?=null

}