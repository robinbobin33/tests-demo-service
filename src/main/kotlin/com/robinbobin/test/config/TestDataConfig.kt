package com.robinbobin.test.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@ConfigurationProperties(prefix = "demo-service.test-data.example-person")
class TestDataConfig {
    lateinit var phone: String
    lateinit var fio: String
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    lateinit var birthDate: LocalDate
}