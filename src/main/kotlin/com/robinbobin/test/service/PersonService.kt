package com.robinbobin.test.service.auth

import org.springframework.stereotype.Service
import com.robinbobin.test.cache.PersonsCache
import com.robinbobin.test.cache.PersonsQueueCache
import com.robinbobin.test.config.TestDataConfig
import com.robinbobin.test.exception.DuplicatePhoneNumberException
import com.robinbobin.test.exception.PersonNotFoundException
import com.robinbobin.test.exception.UnauthorizedException
import com.robinbobin.test.model.Person
import com.robinbobin.test.model.PersonQueueStatus
import com.robinbobin.test.model.QueueItem
import com.robinbobin.test.model.response.AddPersonAsyncResponse
import com.robinbobin.test.util.getLogger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*


@Service
class PersonService(
    private val testDataConfig: TestDataConfig,
    private val personsRepo: PersonsCache,
    private val personsQueue: PersonsQueueCache
) {
    private val examplePerson = Person(
        testDataConfig.phone.toLong(),
        testDataConfig.fio,
        testDataConfig.birthDate)
    private val hasSamePhoneButDifferentFields: (Person, Person) -> Boolean = { p1, p2 ->
        (p1.phone == p2.phone) && (p1 != p2)
    }

    fun addPerson(person: Person) {
        log.info("Adding person $person")
        if (personsRepo.getAll().any { hasSamePhoneButDifferentFields(it, person) }) {
            log.error("Phone number ${person.phone} already exists")
            throw DuplicatePhoneNumberException("Duplicate phone number")
        } else {
            personsRepo.put(person.phone, person)
        }
    }

    fun getPerson(personPhone: Long): Person {
        log.info("Getting person with phone $personPhone")
        if (personPhone == examplePerson.phone) {
            return examplePerson
        }
        val person = personsRepo.get(personPhone)
        if (person == null) {
            val errorMessage = "Person with phone $personPhone not found"
            log.error(errorMessage)
            throw PersonNotFoundException(errorMessage)
        } else {
            return person
        }
    }

    fun deletePerson(personPhone: Long) {
        log.info("Deleting person with phone $personPhone")
        if (personPhone == examplePerson.phone) {
            val errorMessage = "You cant delete person with phone $personPhone"
            log.error(errorMessage)
            throw UnauthorizedException(errorMessage)
        }
        personsRepo.remove(personPhone)
    }

    fun getPersonQueueStatus(personQueueItemId: String): PersonQueueStatus  {
        log.info("Getting queue item with id $personQueueItemId")
        val item = personsQueue.get(personQueueItemId)
        if (item == null) {
            val errorMessage = "person queue item with id $personQueueItemId not found"
            log.error(errorMessage)
            throw PersonNotFoundException(errorMessage)
        } else {
            return item.status
        }
    }

    fun addPersonAsync(person: Person): AddPersonAsyncResponse {
        val id = UUID.randomUUID().toString()
        personsQueue.put(id, QueueItem(id, PersonQueueStatus.InProgress, person))
        GlobalScope.launch {
            delay(5000)
            if (personsRepo.getAll().any { hasSamePhoneButDifferentFields(it, person) }) {
                val errorMessage = "person with phone ${person.phone} already exists"
                personsQueue.put(
                    id,
                    QueueItem(id, PersonQueueStatus.Failure(errorMessage), person)
                )
            } else {
                personsQueue.put(id, QueueItem(id, PersonQueueStatus.Success, person))
                personsRepo.put(person.phone, person)
            }
        }
        return AddPersonAsyncResponse(id)
    }

    companion object {
        val log = getLogger<PersonService>()
    }

}