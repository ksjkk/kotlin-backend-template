package com.example.basic.config.security.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JsonWebToken(
    principal: String,
    authorities: Set<GrantedAuthority> = setOf(),
) : UsernamePasswordAuthenticationToken(principal, "", authorities)