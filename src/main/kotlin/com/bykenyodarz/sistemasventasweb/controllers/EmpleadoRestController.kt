package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Empleado
import com.bykenyodarz.sistemasventasweb.services.apis.EmpleadoServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/empleados")
@Api(tags = ["empleado"])
class EmpleadoRestController(serviceAPI: EmpleadoServiceAPI) : GenericRestController<Empleado, Int>(serviceAPI) {

    @Autowired
    private val empleadoServiceAPI: EmpleadoServiceAPI

    init {
        serviceAPI.also { this.empleadoServiceAPI = it }
    }

    @GetMapping("validar/{user}/{dni}")
    fun validarEmpleado(@PathVariable user: String, @PathVariable dni: String): ResponseEntity<*> {
        return when (val empleado = empleadoServiceAPI.findEmpleadoByUserAndDni(user, dni)) {
            null -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no Encontrado")
            else -> ResponseEntity.ok().body(empleado)
        }
    }

}
