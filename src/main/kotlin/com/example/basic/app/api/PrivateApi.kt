package com.example.basic.app.api

import com.example.basic.app.common.model.ApiResult
import com.example.basic.app.domain.dto.BasicDto
import com.example.basic.app.service.BasicService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/papi")
class PrivateApi(
    private val basicService: BasicService
) {
    @PostMapping("/v1/basic")
    fun save(@RequestBody dto: BasicDto) = ApiResult.succeed(basicService.save(dto))
}