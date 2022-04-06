package com.example.basic.app.api

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/papi")
class PrivateApi {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping(value = ["/v1/private/resource"])
    fun getPrivate(request: HttpServletRequest): String {
//        return request.getHeader("x-api-key")
        return "good"
    }
}