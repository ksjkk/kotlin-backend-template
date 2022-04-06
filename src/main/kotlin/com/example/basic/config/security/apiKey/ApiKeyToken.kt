package com.example.basic.config.security.apiKey

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class ApiKeyToken(
    principal: String?,
    credentials: String,
    authorities: List<GrantedAuthority> = listOf()
): AbstractAuthenticationToken(authorities) {

    constructor(apiKey: String): this(principal = null, credentials = apiKey)

    private var principal: String?
    private var credentials: String

    init {
        this.principal = principal
        this.credentials = credentials
        this.isAuthenticated = principal.orEmpty().isNotBlank()
    }

    override fun getCredentials() = credentials
    override fun getPrincipal() = principal
}