package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.DetalleVenta
import com.bykenyodarz.sistemasventasweb.services.apis.DetalleVentaServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/detalle")
@Api(tags = ["detalle"])
class DetalleVentaRestController(serviceAPI: DetalleVentaServiceAPI) :
    GenericRestController<DetalleVenta, Int>(serviceAPI)