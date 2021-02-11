package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Cliente
import com.bykenyodarz.sistemasventasweb.services.apis.ClienteServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/clientes")
@Api(tags = ["clientes"])
class ClienteRestController(serviceAPI: ClienteServiceAPI) : GenericRestController<Cliente, Int>(serviceAPI)