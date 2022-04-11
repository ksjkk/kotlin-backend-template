package com.example.basic.common.model

class ApiResult<T>(
    var data: T?,
    var message: String?
) {
    companion object {
        fun <T> succeed(data: T): ApiResult<T> = ApiResult(
            data = data,
            message = null
        )

        fun <T> failure(error: String?): ApiResult<T> = ApiResult(
            data = null,
            message = error ?: "에러메세지없음"
        )
    }
}