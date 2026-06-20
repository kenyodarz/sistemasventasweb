package com.bykenyodarz.sistemasventasweb.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SwaggerConfigurationTest {

    @Test
    fun testCustomOpenAPI() {
        val config = SwaggerConfiguration()
        val openAPI = config.customOpenAPI()
        assertNotNull(openAPI)
        assertNotNull(openAPI.info)
        assertEquals("API Sistema de Ventas", openAPI.info.title)
        assertEquals("1.0", openAPI.info.version)
        assertEquals("Demo API Sistema de Ventas con Spring Boot 4 y Kotlin", openAPI.info.description)
    }
}
