package com.robinbobin.test.service.auth

import com.robinbobin.test.config.AuthConfig
import org.springframework.stereotype.Service
import com.robinbobin.test.exception.UnauthorizedException
import org.springframework.beans.factory.annotation.Value

@Service
class AuthService(private val config: AuthConfig) {

    fun checkToken(token: String) {
        if (token != config.token) {
            throw UnauthorizedException("Wrong token")
        }
    }
}