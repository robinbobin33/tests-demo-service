package com.robinbobin.test.util

import com.robinbobin.test.model.Person
import java.time.LocalDate

object RandomGenerator {

    fun getRandomEngString(len: Int): String {
        val chars = ('A'..'Z') + ('a'..'z')
        return (1..len)
            .map { chars.random() }
            .joinToString()
    }

    fun getRandomRuString(len: Int): String {
        val chars = ('А'..'Я') + ('а'..'я')
        return (1..len)
            .map { chars.random() }
            .joinToString()
    }

    fun getRandomPhone(): Long = (70000000000L..79999999999L).random()

    fun getRandomDate(): LocalDate = LocalDate.now().minusDays((1L..999999L).random())

    fun getRandomPerson() = Person(getRandomPhone(), getRandomRuString(10), getRandomDate())

}