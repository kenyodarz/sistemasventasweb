package com.bykenyodarz.sistemasventasweb.shared.application

import com.bykenyodarz.sistemasventasweb.shared.domain.GenericServiceAPI

abstract class AbstractCrudServiceOperations<E : Any, ID : Any>(
    protected val repositoryPort: GenericServiceAPI<E, ID>
) : GenericServiceAPI<E, ID> {

    override fun getById(id: ID): E? {
        return repositoryPort.getById(id)
    }

    override fun save(entity: E): E {
        return repositoryPort.save(entity)
    }

    override fun getAll(): List<E> {
        return repositoryPort.getAll()
    }

    override fun deleteById(id: ID) {
        repositoryPort.deleteById(id)
    }

    override fun update(entity: E): E {
        return repositoryPort.update(entity)
    }
}
