package com.example.basic.config.security

import com.example.basic.config.jwt.JwtFilter
import com.example.basic.config.jwt.JwtProvider
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsUtils
import org.springframework.web.filter.CorsFilter
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
class WebSecurityConfig {

    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE)
    class DefaultSecurityConfig(
        private val jwtProvider: JwtProvider,
        private val corsFilter: CorsFilter
    ): WebSecurityConfigurerAdapter() {

        private val jwtFilter = JwtFilter(this.jwtProvider)

        companion object {
            private val IGNORE_PATH = arrayOf(
                "/health",
                "/ping",
                "/oapi/**"
            )
        }

        override fun configure(web: WebSecurity) {
            web.ignoring().antMatchers(*IGNORE_PATH)
        }

        override fun configure(http: HttpSecurity) {
            http
                .csrf()
                    .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
                .exceptionHandling {
                    it
                        .authenticationEntryPoint { _, response, _ -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
                        .accessDeniedHandler { _, response, _ -> response.sendError(HttpServletResponse.SC_FORBIDDEN) }
                }
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                .antMatchers("/api/**").hasRole("USER")
        }
    }
}