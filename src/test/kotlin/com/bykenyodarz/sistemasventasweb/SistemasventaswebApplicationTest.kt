package com.bykenyodarz.sistemasventasweb

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SistemasventaswebApplicationTest {

    @Test
    fun contextLoads() {
        val app = SistemasventaswebApplication()
        assertNotNull(app)
    }
}
