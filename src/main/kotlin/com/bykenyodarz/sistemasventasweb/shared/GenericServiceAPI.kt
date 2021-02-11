package com.bykenyodarz.sistemasventasweb.shared

import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable

interface GenericServiceAPI<T, ID : Serializable> {

    fun getAll(): List<T>

    fun getOne(id: ID): T?

    fun save(entity: T): T

    fun delete(id: ID)

    fun getRepository(): JpaRepository<T, ID>

}