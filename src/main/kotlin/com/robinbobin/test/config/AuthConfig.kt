package com.robinbobin.test.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "demo-service.auth")
class AuthConfig {
    lateinit var token: String
}