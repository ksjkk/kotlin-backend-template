package com.example.basic.basic.dto

import com.example.basic.domain.basic.Basic
import javax.validation.constraints.NotEmpty

data class BasicDto(
    var id: Long = 0,

    @field:NotEmpty(message = "빈 값을 허용하지 않습니다")
    var content: String = ""
) {

    fun toEntity() = Basic(
        content = this.content
    )
}