package com.robinbobin.test.service.person

import com.robinbobin.test.util.RandomGenerator
import com.robinbobin.test.util.ServiceConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class AddPerson {

    val personService = ServiceConfig.getPersonService()

    @TestFactory
    fun `Add person test cases`() = listOf(
        "min phone number" to 70000000000L,
        "max phone number" to 79999999999L
    ).map { (phoneType, phone) ->
        DynamicTest.dynamicTest("Add person with $phoneType should succeed") {
            val person = RandomGenerator.getRandomPerson().copy(phone = phone)
            personService.addPerson(person)
            val addedPerson = personService.getPerson(person.phone)

            Assertions.assertEquals(person, addedPerson)
        }
    }

}