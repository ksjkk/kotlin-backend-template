package com.example.basic.app.common.exception

import com.example.basic.app.common.model.ApiResult
import com.example.basic.app.common.model.ApiResult.Companion.failure
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(annotations = [RestController::class])
class Exception {


    @ExceptionHandler
    fun noEntityFoundException(e: NoEntityFoundException): ApiResult<String> {
        return failure(e.message)
    }

    @ExceptionHandler
    fun badParameterException(e: BadParameterException): ApiResult<String> {
        return failure(e.message)
    }

    @ExceptionHandler
    fun runtimeException(e: RuntimeException): ApiResult<String> {
        return failure(e.message)
    }
}

open class BadParameterException(paramName: String = ""): RuntimeException("올바르지 않은 Parameter : $paramName")
open class NoEntityFoundException: RuntimeException("데이터를 찾지 못했습니다")

