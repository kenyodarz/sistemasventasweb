package com.bykenyodarz.sistemasventasweb.shared.infrastructure

import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI
import org.springframework.data.jpa.repository.JpaRepository

abstract class AbstractAdapterOperations<E : Any, D : Any, ID : Any, R : JpaRepository<D, ID>>(
    protected val repository: R,
    protected val toDomain: (D) -> E,
    protected val toData: (E) -> D
) : GenericServiceAPI<E, ID> {

    override fun getById(id: ID): E? {
        val optional = repository.findById(id)
        return optional.map(toDomain).orElse(null)
    }

    override fun save(entity: E): E {
        val data = toData(entity)
        val savedData = repository.save(data)
        return toDomain(savedData)
    }

    override fun getAll(): List<E> {
        return repository.findAll().map(toDomain)
    }

    override fun deleteById(id: ID) {
        repository.deleteById(id)
    }

    override fun update(entity: E): E {
        return save(entity)
    }
}

