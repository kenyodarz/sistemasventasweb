package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Venta
import com.bykenyodarz.sistemasventasweb.services.apis.VentaServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import com.bykenyodarz.sistemasventasweb.util.GenerarSerie
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/ventas")
@Api(tags = ["venta"])
class VentasRestController(override var serviceAPI: VentaServiceAPI) : GenericRestController<Venta, Int>(serviceAPI) {


    override fun save(entity: Venta, result: BindingResult): ResponseEntity<*> {
        val generarSerie = GenerarSerie()
        entity.numeroSerie = generarSerie.numeroSerie(serviceAPI.findMAxNumeroSerie())

        return super.save(entity, result)
    }
}
