package com.example.basic.app.service

import com.example.basic.app.common.model.EntityWrapper.Companion.wrapped
import com.example.basic.app.domain.dto.BasicDto
import com.example.basic.app.domain.entity.Basic
import com.example.basic.app.infrastructure.repository.BasicRepository
import com.example.basic.app.service.MockData.Companion.basic
import com.example.basic.app.service.MockData.Companion.basicList
import com.example.basic.app.service.MockData.Companion.convertBasic
import com.example.basic.app.service.MockData.Companion.requestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.Ordered
import java.util.*


@ExtendWith(MockitoExtension::class)
internal class BasicServiceTest {
    @InjectMocks
    private lateinit var basicService: BasicService

    @Mock
    private lateinit var mockBasicRepository: BasicRepository

    @Test
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @DisplayName("엔터티 1개 저장하기")
    fun save(){
        `when`(mockBasicRepository.save(convertBasic)).thenReturn(basic)
        val result = basicService.save(requestDto)

        assertThat(result.id).isEqualTo(1)
    }

    @Test
    @DisplayName("아이디로 1개 조회하기")
    fun findById(){
        val id: Long = 1

        `when`(mockBasicRepository.findById(id)).thenReturn(Optional.of(basic(id)))
        val result = basicService.findById(id).wrapped()

        assertThat(result.id).isEqualTo(1)
    }

    @Test
    @DisplayName("전체 조회하기")
    fun findAll(){
        `when`(mockBasicRepository.findAll()).thenReturn(basicList)
        val result = basicService.findAll()

        assertThat(result).isNotEmpty
        assertThat(result.size).isEqualTo(2)
    }
}

class MockData {
    companion object {
        private const val content: String = "mockContent"
        val requestDto: BasicDto = BasicDto(content = content)
        val convertBasic: Basic = Basic(content = requestDto.content)
        val basic: Basic = Basic(id = 1, content = content)
        val basicList: List<Basic> = listOf(basic, Basic(id = 2, content = content))

        fun basic(id: Long) = Basic(id = id, content = content)
    }
}