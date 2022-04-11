package com.example.basic.common.support

import com.example.basic.common.exception.NoEntityFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface BaseService<ENTITY, ID, DTO> {

    private fun Optional<ENTITY>.getEntity(): ENTITY {
        return this.orElseThrow { NoEntityFoundException() } as ENTITY
    }

    private fun getOptionalEntity(id: ID): Optional<ENTITY> {
        return getRepository().findById(id)
    }

    fun entityToDto(entity: ENTITY): DTO
    fun dtoToEntity(dto: DTO): ENTITY

    fun getRepository(): JpaRepository<ENTITY, ID>

    @Transactional(readOnly = true)
    fun getById(id: ID): DTO {
        return entityToDto(
            getOptionalEntity(id).getEntity()
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
                getOptionalEntity(id).map {
                    updateElement(it)
                    it
                }.getEntity()
            )
        )
    }

    @Transactional
    fun deleteById(id: ID) {
        return getRepository().deleteById(id)
    }
}