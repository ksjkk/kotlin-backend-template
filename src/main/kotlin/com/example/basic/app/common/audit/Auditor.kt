package com.example.basic.app.common.audit

import com.example.basic.app.common.enum.Role
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class Auditor: AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        return try {
            val principal = SecurityContextHolder.getContext().authentication.principal
            if(principal is String){
                Optional.of(principal)
            } else {
                Optional.empty()
            }
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