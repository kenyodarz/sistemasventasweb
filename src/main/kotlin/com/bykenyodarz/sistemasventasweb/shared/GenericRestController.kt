package com.bykenyodarz.sistemasventasweb.shared

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import java.util.function.Consumer
import javax.validation.Valid

@RestController
abstract class GenericRestController<T, ID : Serializable>(val serviceAPI: GenericServiceAPI<T, ID>) {

    fun validar(result: BindingResult): ResponseEntity<*> {
        val errores: MutableMap<String, Any> = HashMap()
        with(result) {
            fieldErrors.forEach(Consumer { err: FieldError ->
                "El campo ${err.field} ${err.defaultMessage}".also { errores[err.field] = it }
            })
        }
        return ResponseEntity.badRequest().body<Map<String, Any>>(errores)
    }

    @GetMapping("/all")
    @ApiOperation(value = "Listar Entidades", notes = "servicio para listar todas las Entidades")
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Entidad listadas correctamente"
        ), ApiResponse(code = 401, message = "Usuario No Autorizado"), ApiResponse(
            code = 403,
            message = "Solicitud prohibida por el servidor"
        ), ApiResponse(code = 404, message = "Entidad no encontrada")]
    )
    fun getAll(): ResponseEntity<List<T>> {
        return ResponseEntity.ok().body(serviceAPI.getAll())
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener una Entidad", notes = "servicio para obtener una Entidad")
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Entidad encontrada correctamente"
        ), ApiResponse(code = 401, message = "Usuario No Autorizado"), ApiResponse(
            code = 403,
            message = "Solicitud prohibida por el servidor"
        ), ApiResponse(code = 404, message = "Entidad no encontrada")]
    )
    fun getOne(@PathVariable id: ID): ResponseEntity<*> {
        val entity: T =
            serviceAPI.getOne(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado")
        return ResponseEntity.ok().body(entity)
    }

    @PostMapping("/save")
    @ApiOperation(value = "Crear/Editar una Entidad", notes = "servicio para crear o editar entidades")
    @ApiResponses(
        value = [ApiResponse(code = 201, message = "Entidad creada correctamente"), ApiResponse(
            code = 401,
            message = "Usuario No Autorizado"
        ), ApiResponse(code = 403, message = "Solicitud prohibida por el servidor"), ApiResponse(
            code = 404,
            message = "Entidad no encontrada"
        )]
    )
    fun save(@RequestBody @Valid entity: T, result: BindingResult): ResponseEntity<*> {
        return if (result.hasErrors()) validar(result)
        else ResponseEntity.status(HttpStatus.CREATED).body(serviceAPI.save(entity))
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "Eliminar una Entidad", notes = "servicio para eliminar entidades")
    @ApiResponses(
        value = [ApiResponse(code = 200, message = "Entidad eliminada correctamente"), ApiResponse(
            code = 401,
            message = "Usuario No Autorizado"
        ), ApiResponse(code = 403, message = "Solicitud prohibida por el servidor"), ApiResponse(
            code = 404,
            message = "Entidad no encontrada"
        )]
    )
    fun delete(@PathVariable id: ID): ResponseEntity<Any> {
        val entity: T = serviceAPI.getOne(id) ?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        serviceAPI.delete(id)
        return ResponseEntity.ok().body(entity)
    }

}
