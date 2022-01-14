package com.robinbobin.test.service.auth

import com.robinbobin.test.config.AuthConfig
import com.robinbobin.test.exception.UnauthorizedException
import com.robinbobin.test.util.ServiceConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckTokenTest {

    val authService = ServiceConfig.getAuthService()

    @Test
    fun `Auth with valid token should succeed`() {
        authService.checkToken(ServiceConfig.authToken)
    }

    @Test
    fun `Auth with non existing token should fail`() {
        val invalidToken = UUID.randomUUID().toString().replace("-", "")

        val exception = assertThrows<UnauthorizedException> {
           authService.checkToken(invalidToken)
        }

        assertEquals("Wrong token", exception.reason)
    }
}