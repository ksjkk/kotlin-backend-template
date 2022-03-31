package com.example.basic.app.common.enum

enum class Role(val description: String) {
    ROLE_SERVER("Server"),
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("일반 유저"),
    UNKNOWN("알수없음")
    ;
    companion object{
        fun findByName(name: String?) = values().find { it.name == name } ?: UNKNOWN
    }
}


enum class YN {
    Y,N
}