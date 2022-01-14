package com.robinbobin.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
open class TestsDemoServiceApplication

fun main(args: Array<String>) {
    runApplication<TestsDemoServiceApplication>(*args)
}