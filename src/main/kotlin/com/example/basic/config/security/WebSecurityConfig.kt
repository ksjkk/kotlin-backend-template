package com.example.basic.config.security

import com.example.basic.config.security.apiKey.ApiKeyFilter
import com.example.basic.config.security.apiKey.ApiKeyProvider
import com.example.basic.config.security.jwt.JwtFilter
import com.example.basic.config.security.jwt.JwtProvider
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@EnableWebSecurity
class WebSecurityConfig {

    companion object {
        fun requestMatcher(path: String) = AntPathRequestMatcher(path)
        fun authenticationManager(authenticationProvider: AuthenticationProvider) = ProviderManager(authenticationProvider)
    }

    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE)
    class PublicSecurityConfig: WebSecurityConfigurerAdapter() {

        companion object {
            private val SWAGGER_PATH = arrayOf(
                "/",
                "/actuator/**",
                "/error",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v2/api-docs**",
                "/webjars/**"

            )
            private val PUBLIC_PATH = arrayOf(
                "/health",
                "/ping",
                *SWAGGER_PATH
            )
        }

        override fun configure(web: WebSecurity) {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        }

        override fun configure(http: HttpSecurity) {
            http
                .csrf().disable()
                .sessionManagement().disable()
                .requestMatchers {
                    it.antMatchers(*PUBLIC_PATH)
                }.authorizeRequests {
                    it.anyRequest().permitAll()
                }
        }
    }

    @Configuration
    @Order(10)
    class ResourceSecurityConfig(
        private val jwtProvider: JwtProvider
    ): WebSecurityConfigurerAdapter() {

        companion object {
            private const val RESOURCE_PATH = "/api/**"
        }

        override fun configure(http: HttpSecurity) {
            http
                .csrf().disable()
                .sessionManagement().disable()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .requestMatcher(requestMatcher(RESOURCE_PATH))
                .authorizeRequests {
                    it.anyRequest().permitAll()
                }
                .addFilterBefore(
                    JwtFilter(requestMatcher(RESOURCE_PATH), authenticationManager(jwtProvider), jwtProvider),
                    AbstractPreAuthenticatedProcessingFilter::class.java
                )
        }
    }

    @Configuration
    @Order(20)
    class PrivateSecurityConfig(
        private val apiKeyProvider: ApiKeyProvider
    ): WebSecurityConfigurerAdapter() {

        companion object {
            private const val PRIVATE_PATH = "/papi/**"
        }

        override fun configure(http: HttpSecurity) {
            http
                .csrf().disable()
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .requestMatcher(requestMatcher(PRIVATE_PATH))
                .authorizeRequests {
                    it.anyRequest().permitAll()
                }
                .addFilterBefore(
                    ApiKeyFilter(requestMatcher(PRIVATE_PATH), authenticationManager(apiKeyProvider)),
                    AbstractPreAuthenticatedProcessingFilter::class.java
                )
        }
    }

    @Configuration
    @Order(Ordered.LOWEST_PRECEDENCE)
    class DefaultSecurityConfig: WebSecurityConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http
                .csrf().disable()
                .sessionManagement().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .anyRequest().authenticated()
        }
    }
}