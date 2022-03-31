package com.example.basic.app.api

import com.example.basic.app.common.enum.Role
import com.example.basic.config.jwt.JwtProvider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CommonApi(
    private val jwtProvider: JwtProvider
) {

    @GetMapping(value = ["/ping"])
    fun ping() = "pong"

    @GetMapping(value = ["/health"])
    fun health() = ResponseEntity.ok()

    @PostMapping(value = ["/oapi/create-token/{id}"])
    fun createToken(@PathVariable id: String): String? = jwtProvider.createToken(id, setOf(Role.ROLE_USER))
}