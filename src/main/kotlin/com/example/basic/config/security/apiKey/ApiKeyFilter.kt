package com.example.basic.config.security.apiKey

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiKeyFilter(
    requestMatcher: RequestMatcher,
    authenticationManager: AuthenticationManager
): AbstractAuthenticationProcessingFilter(requestMatcher, authenticationManager) {
    companion object {
        const val API_KEY_HEADER = "x-api-key"
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        return request.getHeader(API_KEY_HEADER).let {
            it ?: throw InsufficientAuthenticationException("no api key")
            val apiKeyAuthentication = ApiKeyToken(it)
            authenticationManager.authenticate(apiKeyAuthentication)
        }
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