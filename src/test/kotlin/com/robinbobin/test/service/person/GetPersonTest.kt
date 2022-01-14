package com.robinbobin.test.service.person

import com.robinbobin.test.exception.PersonNotFoundException
import com.robinbobin.test.util.RandomGenerator
import com.robinbobin.test.util.ServiceConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows


class GetPersonTest {

    var personService = ServiceConfig.getPersonService()

    @Test
    fun `Get default person should succeed`() {
        val result = personService.getPerson(ServiceConfig.defaultPerson.phone)

        assertEquals(ServiceConfig.defaultPerson, result)
    }

    @Test
    fun `Get non existing person phone number should fail`() {
        val randomPhone = RandomGenerator.getRandomPhone()
        val ex = assertThrows<PersonNotFoundException> {
            personService.getPerson(randomPhone)
        }

        assertEquals("Person with phone $randomPhone not found", ex.reason)
    }

}