package com.example.basic.config.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.util.*


internal class JwtConfigTest {

    private val log = LoggerFactory.getLogger(javaClass)

    @Test
    @DisplayName("새로운 키를 생성한다")
    fun createNewSecretKey() {
        //given
        val key = Keys.secretKeyFor(SignatureAlgorithm.HS512)
        //when
        val secret = Encoders.BASE64.encode(key.encoded)
        //then
        log.debug("secret : $secret")
        assertThat(secret).isNotEmpty
    }

    @Test
    @DisplayName("토근을 생성한다")
    fun createJsonWebTokenWithSecretKey() {
        // given
        val id = "jun.ior"
        val authorities = "ROLE_USER,ROLE_ADMIN"
        val keyBytes = Decoders.BASE64.decode("Q0LHGlS1qelLKND+Ig4A684DoRO5irXY6OWUtHQ70APQLJ9tkgHp32SzKTX9lZgZL3k5NyRowNEjNBjyEe6N1A==")
        val key = Keys.hmacShaKeyFor(keyBytes)
        val now: Long = Date().time
        val validity = Date(now + 360000000) // 100시간

        //when
        val token = Jwts.builder()
            .setSubject(id)
            .claim("project-auth", authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()

        //then
        log.debug("====token====")
        log.debug(token)
        log.debug("====token====")
        assertThat(token).isNotEmpty
    }
}