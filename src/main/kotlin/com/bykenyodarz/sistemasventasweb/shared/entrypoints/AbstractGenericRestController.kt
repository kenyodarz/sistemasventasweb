package com.bykenyodarz.sistemasventasweb.shared.entrypoints

import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.io.Serializable
import java.util.function.Consumer
import jakarta.validation.Valid

abstract class AbstractGenericRestController<E : Any, ID : Serializable, REQ : Any, RES : Any>(
    protected val serviceAPI: GenericServiceAPI<E, ID>,
    protected val toEntity: (REQ) -> E,
    protected val toResponse: (E) -> RES
) {

    protected fun validar(result: BindingResult): ResponseEntity<*> {
        val errores: MutableMap<String, Any> = HashMap()
        with(result) {
            fieldErrors.forEach(Consumer { err: FieldError ->
                "El campo ${err.field} ${err.defaultMessage}".also { errores[err.field] = it }
            })
        }
        return ResponseEntity.badRequest().body<Map<String, Any>>(errores)
    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<RES>> {
        val list = serviceAPI.getAll().map(toResponse)
        return ResponseEntity.ok().body(list)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: ID): ResponseEntity<*> {
        val entity = serviceAPI.getById(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado")
        return ResponseEntity.ok().body(toResponse(entity))
    }

    @PostMapping("/save")
    fun save(@RequestBody @Valid request: REQ, result: BindingResult): ResponseEntity<*> {
        if (result.hasErrors()) {
            return validar(result)
        }
        val entity = toEntity(request)
        val savedEntity = serviceAPI.save(entity)
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(savedEntity))
    }

    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: ID): ResponseEntity<Any> {
        val entity = serviceAPI.getById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        serviceAPI.deleteById(id)
        return ResponseEntity.ok().body(toResponse(entity) as Any)
    }
}
