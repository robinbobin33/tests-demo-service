package com.robinbobin.test.service.person

import com.robinbobin.test.exception.PersonNotFoundException
import com.robinbobin.test.exception.UnauthorizedException
import com.robinbobin.test.util.RandomGenerator
import com.robinbobin.test.util.ServiceConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeletePerson {

    val personService = ServiceConfig.getPersonService()

    @Test
    fun `Delete existing person should succeed`() {
        val person = RandomGenerator.getRandomPerson()
        personService.addPerson(person)
        personService.deletePerson(person.phone)

        assertThrows<PersonNotFoundException> {
            personService.getPerson(person.phone)
        }
    }

    @Test
    fun `Delete not existing person should be idempotent and succeed`() {
        val phone = RandomGenerator.getRandomPhone()

        assertThrows<PersonNotFoundException> {
            personService.getPerson(phone)
        }
    }

    @Test
    fun `Delete default user should fail`() {
        val ex = assertThrows<UnauthorizedException> {
            personService.deletePerson(ServiceConfig.defaultPerson.phone)
        }

        assertEquals("You cant delete person with phone ${ServiceConfig.defaultPerson.phone}", ex.reason)
    }
}