package com.bykenyodarz.sistemasventasweb.util

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("API Sistema de Ventas")
                    .version("1.0")
                    .description("Demo API Sistema de Ventas con Spring Boot 4 y Kotlin")
            )
    }
}
