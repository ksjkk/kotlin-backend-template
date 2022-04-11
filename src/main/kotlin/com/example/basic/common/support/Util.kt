package com.example.basic.common.support

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Util {

    companion object {
        inline fun<reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.javaClass)
    }
}