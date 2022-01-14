package com.robinbobin.test.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class Person(
    @field:Min(70000000000)
    @field:Max(79999999999)
    val phone: Long,
    val fio: String,
    @field:JsonFormat(pattern = "dd.MM.yyyy") val birthDate: LocalDate)