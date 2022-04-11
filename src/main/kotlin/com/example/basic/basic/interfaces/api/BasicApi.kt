package com.example.basic.basic.interfaces.api

import com.example.basic.basic.application.BasicService
import com.example.basic.basic.dto.BasicDto
import com.example.basic.common.model.ApiResult
import com.example.basic.common.model.ApiResult.Companion.succeed
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class BasicApi(
    private val basicService: BasicService
) {

    @PostMapping("/v1/basic")
    fun save(@RequestBody dto: BasicDto): ApiResult<BasicDto> {
        return succeed(basicService.createByDto(dto))
    }

    @PatchMapping("/v1/basic/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody dto: BasicDto): ApiResult<BasicDto> {
        return succeed(
            basicService.updateBasic(dto)
        )
    }

    @GetMapping("/v1/basic/{id}")
    fun findById(@PathVariable id: Long): ApiResult<BasicDto> {
        return succeed(basicService.getById(id))
    }

    @GetMapping("/v1/basics")
    fun findAll(): ApiResult<List<BasicDto>> {
        return succeed(basicService.getAllBasics())
    }

    @DeleteMapping("/v1/basic/{id}")
    fun deleteById(@PathVariable id: Long): ApiResult<Unit> {
        return succeed(basicService.deleteById(id))
    }
}