package com.example.basic.config.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebAppConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.maxAge = 3600L
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}

@Configuration
class WebMvcConfig: WebMvcConfigurer {
    companion object {
        private val CLASSPATH_PATH_PATTERNS = arrayOf(
            "/webjars/**"
        )
        private val CLASSPATH_RESOURCE_LOCATIONS = arrayOf(
            "classpath:/META-INF/resources/",
            "classpath:/META-INF/resources/webjars/"
        )
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler(*CLASSPATH_PATH_PATTERNS).addResourceLocations(*CLASSPATH_RESOURCE_LOCATIONS)
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/", "/swagger-ui/index.html")
    }
}