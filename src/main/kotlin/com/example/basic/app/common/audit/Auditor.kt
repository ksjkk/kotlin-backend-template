package com.example.basic.app.common.audit

import com.example.basic.app.common.enum.Role
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class Auditor: AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        return try {
            val authentication = SecurityContextHolder.getContext().authentication

            Optional.of(
                when(val principal = authentication.principal) {
                    is String -> principal
                    else -> ""
                }
            )
        } catch (e: Exception) {
            Optional.empty()
        }
    }

    val current: String = currentAuditor.orElseGet { "" }

    val roles: List<Role>
        get() {
            return try {
                val authentication = SecurityContextHolder.getContext().authentication
                authentication.authorities.map { Role.findByName(it.authority) }
            } catch (e: Exception) {
                listOf(Role.UNKNOWN)
            }
        }
}