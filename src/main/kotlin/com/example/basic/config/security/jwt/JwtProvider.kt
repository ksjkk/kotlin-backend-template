package com.example.basic.config.security.jwt

import com.example.basic.common.enum.Role
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*


@Component
class JwtProvider(
    @Value("\${jwt.secret}")
    private val secret: String,

    @Value("\${jwt.validityHour}")
    private val validityHour: Long
): AuthenticationProvider {
    private var key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    companion object {
        private const val AUTHORITIES_KEY = "project-auth";
    }

    fun createToken(userId: String, authoritySet: Set<Role>): String? {
        val authorities = authoritySet.joinToString(",")

        val validity = Date(Date().time + this.validityHour * 3600000)
        return Jwts.builder()
            .setSubject(userId)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun getParsedToken(token: String): Authentication? {
        val claims = validateAndParsedToken(token)
        val authorities: Set<GrantedAuthority> = claims[AUTHORITIES_KEY].toString().split(",").map { SimpleGrantedAuthority(it) }.toSet()
        return JsonWebToken(claims.subject, authorities)
    }

    private fun validateAndParsedToken(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch(e: Exception) {
            throw when(e) {
                is ExpiredJwtException, is UnsupportedJwtException, is MalformedJwtException, is SignatureException, is IllegalArgumentException -> InsufficientAuthenticationException("wrong json web token")
                else -> e
            }
        }
    }

    override fun authenticate(authentication: Authentication): Authentication {
        val userId = authentication.principal as String
        val authoritySet = authentication.authorities.toSet()
        return JsonWebToken(userId, authoritySet)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication == JsonWebToken::class.java
    }
}