package com.example.basic.app.infrastructure.repository

import com.example.basic.app.domain.entity.ApiKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApiKeyRepository: JpaRepository<ApiKey, Long> {

    fun findByApiKey(apiKey: String): Optional<ApiKey>
}