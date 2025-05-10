package org.scaleadvisor.backend.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        val jwtSchemeName = "jwtAuth"

        val securityScheme = SecurityScheme()
            .name(HttpHeaders.AUTHORIZATION)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)

        val securityRequirement = SecurityRequirement().addList(jwtSchemeName)

        val components = Components()
            .addSecuritySchemes(jwtSchemeName, securityScheme)

        return OpenAPI()
            .components(components)
            .addSecurityItem(securityRequirement)
            .info(
                Info()
                    .title("ScaleAdvisor API")
                    .description("ScaleAdvisor API 명세서입니다.")
            )
    }
}