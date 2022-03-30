package com.example.basic.app.common.model

import com.example.basic.app.common.exception.NoEntityFoundException
import java.util.*

class EntityWrapper {
    companion object {
        fun <ENTITY> Optional<ENTITY>.wrapped(): ENTITY {
            return this.orElseThrow { NoEntityFoundException() } as ENTITY
        }

        fun <ENTITY> ENTITY?.wrapped(): ENTITY {
            return this ?: throw NoEntityFoundException()
        }
    }
}