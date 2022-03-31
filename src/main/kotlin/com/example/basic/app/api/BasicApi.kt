package com.example.basic.app.api

import com.example.basic.app.common.model.ApiResult.Companion.succeed
import com.example.basic.app.domain.dto.BasicDto
import com.example.basic.app.service.BasicService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class BasicApi(
    private val basicService: BasicService
) {

    @PostMapping("/v1/basic")
    fun save(@RequestBody dto: BasicDto) = succeed(basicService.save(dto))

    @GetMapping("/v1/basic/{id}")
    fun findById(@PathVariable id: Long) = succeed(basicService.findById(id))

    @GetMapping("/v1/basics")
    fun findAll() = succeed(basicService.findAll())

    @DeleteMapping("/v1/basic/{id}")
    fun deleteById(@PathVariable id: Long) = succeed(basicService.deleteById(id))
}