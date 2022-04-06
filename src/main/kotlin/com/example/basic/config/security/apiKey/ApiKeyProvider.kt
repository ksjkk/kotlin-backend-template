package com.example.basic.config.security.apiKey

import com.example.basic.app.infrastructure.repository.ApiKeyRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class ApiKeyProvider(
    private val apiKeyRepository: ApiKeyRepository
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val apiKey = authentication.credentials
        apiKey as String
        val client = apiKeyRepository.findByApiKey(apiKey).orElseThrow { InsufficientAuthenticationException("wrong api key") }
        return ApiKeyToken(client.clientId, apiKey)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication == ApiKeyToken::class.java
    }
}