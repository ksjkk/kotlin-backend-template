package com.example.basic.config.security.jwt

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtFilter(
    requestMatcher: RequestMatcher,
    authenticationManager: AuthenticationManager,
    private val jwtProvider: JwtProvider
): AbstractAuthenticationProcessingFilter(requestMatcher, authenticationManager) {
    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER).orEmpty()
        return if (bearerToken.isNotBlank() && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.replaceFirst(BEARER_PREFIX.toRegex(), "")
        } else ""
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val token = resolveToken(request)
        val jwt = jwtProvider.getParsedToken(token)
        return authenticationManager.authenticate(jwt)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        // NOTE: super.successfulAuthentication 을 호출하면 successHandler 가 동작하여 기본 설정으로는 "/"로 이동하므로 super 를 호출하지 않음
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }
}