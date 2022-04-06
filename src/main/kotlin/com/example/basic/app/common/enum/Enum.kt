package com.example.basic.app.common.enum

enum class Role(val description: String, val roleName: String) {
    ROLE_ADMIN("ADMIN", "ADMIN"),
    ROLE_USER("일반 유저", "USER"),
    UNKNOWN("알수없음", "")
    ;
    companion object{
        fun findByName(name: String?) = values().find { it.name == name } ?: UNKNOWN
    }
}


enum class YN {
    Y,N
}