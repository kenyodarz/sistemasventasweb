package com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints

import com.bykenyodarz.sistemasventasweb.application.services.ProductoServiceAPI
import com.bykenyodarz.sistemasventasweb.domain.model.Producto
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.CreateProductoRequest
import com.bykenyodarz.sistemasventasweb.infrastructure.entrypoints.dto.ProductoResponse
import com.bykenyodarz.sistemasventasweb.shared.entrypoints.AbstractGenericRestController
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/productos")
@Tag(name = "producto")
class ProductoRestController(
    private val productoServiceAPI: ProductoServiceAPI
) : AbstractGenericRestController<Producto, Int, CreateProductoRequest, ProductoResponse>(
    productoServiceAPI,
    { req ->
        Producto(
            idProducto = req.idProducto,
            nombres = req.nombres,
            precio = req.precio,
            stock = req.stock,
            estado = req.estado
        )
    },
    { entity ->
        ProductoResponse(
            idProducto = entity.idProducto,
            nombres = entity.nombres,
            precio = entity.precio,
            stock = entity.stock,
            estado = entity.estado
        )
    }
) {

    @GetMapping("/with-stock/{id}")
    fun findProductoWithStock(@PathVariable id: Int): ResponseEntity<*> {
        return when (val producto = productoServiceAPI.findProductoWithStock(id)) {
            null -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto inexistente o sin stock")
            else -> ResponseEntity.ok().body(toResponse(producto))
        }
    }

    @GetMapping("/actualizar/{id}/{cantidad}")
    fun actualizarStock(@PathVariable id: Int, @PathVariable cantidad: Int): ResponseEntity<*> {
        return when (val producto = productoServiceAPI.getById(id)) {
            null -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto inexistente o sin stock")
            else -> {
                producto.stock = producto.stock?.minus(cantidad)
                val updatedProducto = productoServiceAPI.save(producto)
                ResponseEntity.ok().body(toResponse(updatedProducto))
            }
        }
    }
}
