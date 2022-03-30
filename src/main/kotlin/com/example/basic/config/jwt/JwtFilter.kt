package com.example.basic.config.jwt

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(
    private val jwtProvider: JwtProvider
) : GenericFilterBean() {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER) ?: ""
        return if (bearerToken.isNotBlank() && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.replaceFirst(BEARER_PREFIX.toRegex(), "")
        } else ""
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val jwt = resolveToken(request as HttpServletRequest)

        if (jwt.isEmpty() || !jwtProvider.validateToken(jwt)) {
            log.warn("인증오류 - jwt : $jwt")
            val res = response as HttpServletResponse
            res.sendError(HttpStatus.UNAUTHORIZED.value())
        } else {
            val authentication: Authentication? = jwtProvider.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
            chain.doFilter(request, response)
        }
    }
}