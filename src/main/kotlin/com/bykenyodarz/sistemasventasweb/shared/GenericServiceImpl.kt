package com.bykenyodarz.sistemasventasweb.shared

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Service
abstract class GenericServiceImpl<T, ID: Serializable>: GenericServiceAPI<T, ID> {

    override fun getAll(): List<T> {
        val returnList: MutableList<T> = ArrayList()
        getRepository().findAll().forEach(returnList::add)
        return returnList
    }

    override fun getOne(id: ID): T? {
        // usamos un Optional de Java8
        val optional: Optional<T> = getRepository().findById(id)
        // Retornamos null en casi de que no hallemos el objeto
        return optional.orElse(null)
    }

    override fun save(entity: T): T {
        return getRepository().save(entity!!)
    }

    override fun delete(id: ID) {
        return getRepository().deleteById(id)
    }

    abstract override fun getRepository(): JpaRepository<T, ID>
}