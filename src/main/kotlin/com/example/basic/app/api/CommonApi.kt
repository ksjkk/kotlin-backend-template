package com.example.basic.app.api

import com.example.basic.app.common.enum.Role
import com.example.basic.config.security.jwt.JwtProvider
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CommonApi(
    private val jwtProvider: JwtProvider
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping(value = ["/ping"])
    fun ping() = "pong"

    @GetMapping(value = ["/health"])
    fun health() = ResponseEntity.ok("health")

    @PostMapping(value = ["/oapi/create-token/{id}"])
    fun createToken(@PathVariable id: String): String? = jwtProvider.createToken(id, setOf(Role.ROLE_USER))

    @PostMapping(value = ["/log"])
    fun logOrderByPriority(){
        log.error("error")
        log.warn("warn")
        log.info("info")
        log.warn("warn")
        log.trace("trace")
    }
}