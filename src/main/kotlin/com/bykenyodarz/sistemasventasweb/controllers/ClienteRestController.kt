package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Cliente
import com.bykenyodarz.sistemasventasweb.services.apis.ClienteServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/clientes")
@Api(tags = ["clientes"])
class ClienteRestController(override var serviceAPI: ClienteServiceAPI) :
    GenericRestController<Cliente, Int>(serviceAPI) {

        @GetMapping("/dni/{dni}")
        fun findByDni(@PathVariable dni: String): ResponseEntity<*>{
            return when(val cliente = serviceAPI.findByDni(dni)) {
                null -> { ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no Encontrado") }
                else -> { ResponseEntity.status(HttpStatus.OK).body(cliente) }
            }
        }

    }
