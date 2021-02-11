package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Venta
import com.bykenyodarz.sistemasventasweb.services.apis.VentaServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/ventas")
@Api(tags = ["venta"])
class VentasRestController(serviceAPI: VentaServiceAPI) : GenericRestController<Venta, Int>(serviceAPI)