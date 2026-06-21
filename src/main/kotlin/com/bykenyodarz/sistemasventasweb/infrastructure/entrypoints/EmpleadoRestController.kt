package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.EmpleadoServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.Empleado
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateEmpleadoRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.EmpleadoResponse
import com.bykenyodarz.sistemasventasweb.shared.entrypoints.AbstractGenericRestController
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/empleados")
@Tag(name = "empleado")
class EmpleadoRestController(
    private val empleadoServiceAPI: EmpleadoServiceAPI
) : AbstractGenericRestController<Empleado, Int, CreateEmpleadoRequest, EmpleadoResponse>(
    empleadoServiceAPI,
    { req ->
        Empleado(
            idEmpleado = req.idEmpleado,
            dni = req.dni,
            nombres = req.nombres,
            telefono = req.telefono,
            estado = req.estado,
            user = req.user
        )
    },
    { entity ->
        EmpleadoResponse(
            idEmpleado = entity.idEmpleado,
            dni = entity.dni,
            nombres = entity.nombres,
            telefono = entity.telefono,
            estado = entity.estado,
            user = entity.user
        )
    }
) {

    @GetMapping("/validar/{user}/{dni}")
    fun validarEmpleado(@PathVariable user: String, @PathVariable dni: String): ResponseEntity<*> {
        return when (val empleado = empleadoServiceAPI.findEmpleadoByUserAndDni(user, dni)) {
            null -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no Encontrado")
            else -> ResponseEntity.ok().body(toResponse(empleado))
        }
    }
}
