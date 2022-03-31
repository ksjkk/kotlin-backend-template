package com.example.basic.app.common.audit

import com.example.basic.app.common.enum.Role
import com.example.basic.config.apiKey.ApiKeyToken
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class Auditor: AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        return try {
            val authentication = SecurityContextHolder.getContext().authentication

            Optional.of(
                when(val principal = authentication.principal) {
                    is UserDetails -> principal.username
                    is ApiKeyToken -> principal.principal!!
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