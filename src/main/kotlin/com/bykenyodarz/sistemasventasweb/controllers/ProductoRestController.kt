package com.bykenyodarz.sistemasventasweb.controllers

import com.bykenyodarz.sistemasventasweb.models.Producto
import com.bykenyodarz.sistemasventasweb.services.apis.ProductoServiceAPI
import com.bykenyodarz.sistemasventasweb.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/productos")
@Api(tags = ["producto"])
class ProductoRestController(override var serviceAPI: ProductoServiceAPI) :
    GenericRestController<Producto, Int>(serviceAPI) {

    @GetMapping("/with-stock/{id}")
    fun findProductoWithStock(@PathVariable id: Int): ResponseEntity<*> {
        return when (val producto: Producto? = this.serviceAPI.findProductoWithStock(id)) {
            null -> {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto inexistente o sin stock")
            }
            else -> {
                ResponseEntity.ok().body(producto)
            }
        }
    }

    @GetMapping("/actualizar/{id}/{cantidad}")
    fun actualizarStock(@PathVariable id: Int, @PathVariable cantidad: Int): ResponseEntity<*>  {
        return when (val producto: Producto? = this.serviceAPI.getOne(id)) {
            null -> {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto inexistente o sin stock")
            }
            else -> {
                producto.stock = producto.stock?.minus(cantidad)
                ResponseEntity.ok().body(this.serviceAPI.save(producto))
            }
        }
    }

}
