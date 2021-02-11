package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Empleado
import com.bykenyodarz.sistemasventasweb.services.apis.EmpleadoServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/empleados")
@Api(tags = ["empleado"])
class EmpleadoRestController(serviceAPI: EmpleadoServiceAPI) : GenericRestController<Empleado, Int>(serviceAPI)