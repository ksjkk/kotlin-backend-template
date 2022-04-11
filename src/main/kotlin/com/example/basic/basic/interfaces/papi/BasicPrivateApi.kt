package com.example.basic.basic.interfaces.papi

import com.example.basic.common.model.ApiResult
import com.example.basic.basic.dto.BasicDto
import com.example.basic.basic.application.BasicService
import com.example.basic.common.model.ApiResult.Companion.succeed
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/papi")
class BasicPrivateApi(
    private val basicService: BasicService
) {

    @PostMapping("/v1/basic")
    fun save(@RequestBody dto: BasicDto): ApiResult<BasicDto> {
        return succeed(
            basicService.createByDto(dto)
        )
    }
}