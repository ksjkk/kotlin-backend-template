package com.example.basic.app.common.model

class ApiResult<T>(
    var data: T?,
    var message: String?
) {
    companion object {
        fun <T> succeed(data: T) = ApiResult(data, null)

        fun failure(error: String?): ApiResult<String> {
            return ApiResult(null, error ?: "메세지없음")
        }
    }
}