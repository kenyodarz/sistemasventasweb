package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.ClienteServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.Cliente
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateClienteRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.ClienteResponse
import com.bykenyodarz.sistemasventasweb.shared.entrypoints.AbstractGenericRestController
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/clientes")
@Tag(name = "clientes")
class ClienteRestController(
    private val clienteServiceAPI: ClienteServiceAPI
) : AbstractGenericRestController<Cliente, Int, CreateClienteRequest, ClienteResponse>(
    clienteServiceAPI,
    { req ->
        Cliente(
            idCliente = req.idCliente,
            dni = req.dni,
            nombres = req.nombres,
            direccion = req.direccion,
            estado = req.estado
        )
    },
    { entity ->
        ClienteResponse(
            idCliente = entity.idCliente,
            dni = entity.dni,
            nombres = entity.nombres,
            direccion = entity.direccion,
            estado = entity.estado
        )
    }
) {

    @GetMapping("/dni/{dni}")
    fun findByDni(@PathVariable dni: String): ResponseEntity<*> {
        return when (val cliente = clienteServiceAPI.findByDni(dni)) {
            null -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no Encontrado")
            else -> ResponseEntity.status(HttpStatus.OK).body(toResponse(cliente))
        }
    }
}
