package com.bykenyodarz.sistemasventasweb.shared.domain

interface GetByIdUseCase<T, ID> {
    fun getById(id: ID): T?
}

interface SaveUseCase<T> {
    fun save(entity: T): T
}

interface GetAllUseCase<T> {
    fun getAll(): List<T>
}

interface DeleteByIdUseCase<ID> {
    fun deleteById(id: ID)
}

interface UpdateUseCase<T> {
    fun update(entity: T): T
}

interface GenericServiceAPI<T, ID> :
    GetByIdUseCase<T, ID>,
    SaveUseCase<T>,
    GetAllUseCase<T>,
    DeleteByIdUseCase<ID>,
    UpdateUseCase<T>
