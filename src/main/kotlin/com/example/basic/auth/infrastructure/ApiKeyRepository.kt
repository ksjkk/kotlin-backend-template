package com.example.basic.auth.infrastructure

import com.example.basic.domain.auth.ApiKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApiKeyRepository: JpaRepository<ApiKey, Long> {

    fun findByApiKey(apiKey: String): Optional<ApiKey>
}