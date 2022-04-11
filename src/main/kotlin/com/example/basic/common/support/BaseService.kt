package com.example.basic.common.support

import com.example.basic.common.exception.NoEntityFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface BaseService<ENTITY, ID, DTO> {

    private fun Optional<ENTITY>.getEntity(id: ID? = null): ENTITY {
        return this.orElseThrow { NoEntityFoundException(id) }
    }

    private fun getOptionalEntity(id: ID): Optional<ENTITY> {
        return getRepository().findById(id)
    }

    private fun getEntity(id: ID): ENTITY {
        return getOptionalEntity(id).getEntity(id)
    }

    fun entityToDto(entity: ENTITY): DTO
    fun dtoToEntity(dto: DTO): ENTITY

    fun getRepository(): JpaRepository<ENTITY, ID>

    @Transactional(readOnly = true)
    fun getById(id: ID): DTO {
        return entityToDto(
            getEntity(id)
        )
    }

    @Transactional(readOnly = true)
    fun getAll(): List<DTO> {
        return getRepository()
            .findAll()
            .map {
                entityToDto(it)
            }
    }

    @Transactional
    fun createByDto(dto: DTO): DTO {
        return entityToDto(
            getRepository().save(dtoToEntity(dto))
        )
    }

    @Transactional
    fun updateEntity(id: ID, updateElement: (ENTITY) -> Unit): DTO {
        return entityToDto(
            getRepository().save(
                Optional.of(getEntity(id)).map {
                    updateElement(it)
                    it
                }.getEntity(id)
            )
        )
    }

    @Transactional
    fun deleteById(id: ID) {
        return getRepository().delete(
            getEntity(id)
        )
    }
}