package com.example.basic.common.exception

import com.example.basic.common.model.ApiResult
import com.example.basic.common.model.ApiResult.Companion.failure
import com.example.basic.common.support.Util.Companion.logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import kotlin.Exception


@RestControllerAdvice
class Exception{
    private val log = logger()

    @ExceptionHandler
    fun noEntityFoundException(e: NoEntityFoundException): ApiResult<String> {
        return failure(e.message)
    }

    @ExceptionHandler
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ApiResult<String> {
        val errorMessage = e.bindingResult.fieldErrors.joinToString(", ") {
            "${it.field} : ${it.defaultMessage}"
        }
        log.warn("dto validation error : $errorMessage")
        return failure(errorMessage)
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

class NoEntityFoundException(
    val id: Any? = null
): RuntimeException(
    "데이터를 찾을 수 없습니다.${if(id == null){ "" } else { " ID : $id" }}"
)