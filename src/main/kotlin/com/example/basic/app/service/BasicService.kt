package com.example.basic.app.service

import com.example.basic.app.common.model.EntityWrapper.Companion.wrapped
import com.example.basic.app.domain.dto.BasicDto
import com.example.basic.app.domain.entity.Basic
import com.example.basic.app.infrastructure.repository.BasicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BasicService(
    private val basicRepository: BasicRepository
) {

    fun findById(id: Long): BasicDto {
        return basicRepository.findById(id).wrapped().toDto()
    }

    fun findAll() = basicRepository.findAll().map { it.toDto() }

    @Transactional
    fun save(dto: BasicDto): BasicDto {
        val basic = basicRepository.save(Basic(content = dto.content))
        dto.id = basic.id
        return dto
    }

    @Transactional
    fun deleteById(id: Long):List<BasicDto> {
        basicRepository.deleteById(id)
        return findAll()
    }
}