package com.example.basic.app.common.exception

import com.example.basic.app.common.model.ApiResult
import com.example.basic.app.common.model.ApiResult.Companion.failure
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import kotlin.Exception


@RestControllerAdvice
class Exception {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler
    fun noEntityFoundException(e: NoEntityFoundException): ApiResult<String> {
        return failure(e.message)
    }

    @ExceptionHandler
    fun runtimeException(e: RuntimeException): ApiResult<String> {
        log.warn(e.stackTrace.toString())
        return failure(e.message)
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun exception(e: Exception): ApiResult<String> {
        log.warn("exception, error : ${e.javaClass}")
        return failure(e.message)
    }
}

class NoEntityFoundException: RuntimeException("데이터를 찾지 못했습니다")

