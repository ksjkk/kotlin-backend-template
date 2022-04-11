package com.example.basic.basic.application

import com.example.basic.basic.dto.BasicDto
import com.example.basic.basic.infrastructure.BasicRepository
import com.example.basic.common.support.BaseService
import com.example.basic.domain.basic.Basic
import org.springframework.stereotype.Service

@Service
class BasicService(
    private val basicRepository: BasicRepository
): BaseService<Basic, Long, BasicDto> {

    override fun entityToDto(entity: Basic) = entity.toDto()

    override fun dtoToEntity(dto: BasicDto) = dto.toEntity()

    override fun getRepository() = basicRepository

    fun updateBasic(dto: BasicDto): BasicDto {
        return updateEntity(dto.id) {
            it.content = dto.content
        }
    }

    fun getAllBasics(): List<BasicDto> {
        return getAll()
    }
}