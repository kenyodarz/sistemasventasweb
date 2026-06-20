package com.bykenyodarz.sistemasventasweb.shared

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import java.util.function.Consumer
import jakarta.validation.Valid

@RestController
abstract class GenericRestController<T : Any, ID : Serializable>(val serviceAPI: GenericServiceAPI<T, ID>) {

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
    @Operation(summary = "Listar Entidades", description = "servicio para listar todas las Entidades")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Entidad listadas correctamente"
        ), ApiResponse(responseCode = "401", description = "Usuario No Autorizado"), ApiResponse(
            responseCode = "403",
            description = "Solicitud prohibida por el servidor"
        ), ApiResponse(responseCode = "404", description = "Entidad no encontrada")]
    )
    fun getAll(): ResponseEntity<List<T>> {
        return ResponseEntity.ok().body(serviceAPI.getAll())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una Entidad", description = "servicio para obtener una Entidad")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Entidad encontrada correctamente"
        ), ApiResponse(responseCode = "401", description = "Usuario No Autorizado"), ApiResponse(
            responseCode = "403",
            description = "Solicitud prohibida por el servidor"
        ), ApiResponse(responseCode = "404", description = "Entidad no encontrada")]
    )
    fun getOne(@PathVariable id: ID): ResponseEntity<*> {
        val entity: T =
            serviceAPI.getOne(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado")
        return ResponseEntity.ok().body(entity)
    }

    @PostMapping("/save")
    @Operation(summary = "Crear/Editar una Entidad", description = "servicio para crear o editar entidades")
    @ApiResponses(
        value = [ApiResponse(responseCode = "201", description = "Entidad creada correctamente"), ApiResponse(
            responseCode = "401",
            description = "Usuario No Autorizado"
        ), ApiResponse(responseCode = "403", description = "Solicitud prohibida por el servidor"), ApiResponse(
            responseCode = "404",
            description = "Entidad no encontrada"
        )]
    )
    fun save(@RequestBody @Valid entity: T, result: BindingResult): ResponseEntity<*> {
        return if (result.hasErrors()) validar(result)
        else ResponseEntity.status(HttpStatus.CREATED).body(serviceAPI.save(entity))
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "Eliminar una Entidad", description = "servicio para eliminar entidades")
    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "Entidad eliminada correctamente"), ApiResponse(
            responseCode = "401",
            description = "Usuario No Autorizado"
        ), ApiResponse(responseCode = "403", description = "Solicitud prohibida por el servidor"), ApiResponse(
            responseCode = "404",
            description = "Entidad no encontrada"
        )]
    )
    fun delete(@PathVariable id: ID): ResponseEntity<Any> {
        val entity: T = serviceAPI.getOne(id) ?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        serviceAPI.delete(id)
        return ResponseEntity.ok().body(entity)
    }

}
