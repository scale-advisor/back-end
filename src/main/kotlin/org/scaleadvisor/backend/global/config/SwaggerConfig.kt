package org.scaleadvisor.backend.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SwaggerConfig(
    @Value("\${app.url}")
    private val appUrl: String,

    @Value("\${app.dev-port:}")
    private val devPort: String,

    @Value("\${server.servlet.context-path}")
    private val contextPath: String
) {

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

        val server: Server
        if (devPort.isNotEmpty()) {
            server = Server()
                .url("${appUrl}:${devPort}${contextPath}")
                .description("Dev Server")

        } else {
            server = Server()
                .url("${appUrl}${contextPath}")
                .description("Dev Server")
        }


        return OpenAPI()
            .servers(listOf(server))
            .components(components)
            .addSecurityItem(securityRequirement)
            .info(
                Info()
                    .title("ScaleAdvisor API")
                    .description("ScaleAdvisor API 명세서입니다.")
            )
    }
}