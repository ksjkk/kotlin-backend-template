package com.example.basic.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


/**
 * Swagger2 기본 설정
 */
@Configuration
@EnableSwagger2
class SwaggerConfig{

    companion object {
        val PATHS = listOf("/api", "/papi")
    }

    @Bean
    fun docket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(
                ApiInfoBuilder()
                    .title("api title")
                    .contact(Contact("jun", "", ""))
                    .description("backend template")
                    .build()
            )
            .securitySchemes(listOf(apiKey(), papiKey()))
            .securityContexts(listOf(apiSecurityContext(), papiSecurityContext()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.basic"))
            .paths {
                PATHS.any { path -> it.startsWith(path) }
            }
            .build()
            .pathMapping("/")
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Access Token", "Authorization", "header")
    }

    private fun papiKey(): ApiKey {
        return ApiKey("API Key", "X-API-Key", "header")
    }

    private fun apiSecurityContext(): SecurityContext {
        return SecurityContext.builder()
            .operationSelector { it.requestMappingPattern().startsWith("/api") }
            .securityReferences(listOf(
                SecurityReference("Access Token", arrayOf(AuthorizationScope("api", "/api 접근"))),
            ))
            .build()
    }

    private fun papiSecurityContext(): SecurityContext {
        return SecurityContext.builder()
            .operationSelector { it.requestMappingPattern().startsWith("/papi") }
            .securityReferences(listOf(
                SecurityReference("API Key", arrayOf(AuthorizationScope("papi", "/papi 접근")))
            ))
            .build()
    }
}