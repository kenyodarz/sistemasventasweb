package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Producto
import com.bykenyodarz.sistemasventasweb.services.apis.ProductoServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/productos")
@Api(tags = ["producto"])
class ProductoRestController(serviceAPI: ProductoServiceAPI) : GenericRestController<Producto, Int>(serviceAPI)