package com.robinbobin.test.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import com.robinbobin.test.exception.PersonNotFoundException
import com.robinbobin.test.exception.UnauthorizedException
import com.robinbobin.test.model.Person
import com.robinbobin.test.model.PersonQueueStatus
import com.robinbobin.test.model.response.AddPersonAsyncResponse
import com.robinbobin.test.model.response.GetPersonQueueStatusResponse
import com.robinbobin.test.service.auth.AuthService
import com.robinbobin.test.service.auth.PersonService
import javax.validation.Valid

@RestController
@RequestMapping("/v1/person")
class PersonController(
    private val personService: PersonService,
    private val authService: AuthService
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun addPerson(
        @RequestHeader("Authorization") authToken: String,
        @Valid @RequestBody person: Person
    ) {
       authService.checkToken(authToken)
       personService.addPerson(person)
    }

    @PostMapping("/async")
    fun addPersonAsync(
        @RequestHeader("Authorization") authToken: String,
        @Valid @RequestBody person: Person
    ): AddPersonAsyncResponse {
        authService.checkToken(authToken)
        return personService.addPersonAsync(person)
    }

    @GetMapping("/async/{queueId}")
    fun addPersonQueueStatus(
        @RequestHeader("Authorization") authToken: String,
        @PathVariable queueId: String
    ): GetPersonQueueStatusResponse {
        authService.checkToken(authToken)
        val status = personService.getPersonQueueStatus(queueId)
        return when (status) {
            is PersonQueueStatus.Success -> GetPersonQueueStatusResponse(queueId, "Success")
            is PersonQueueStatus.InProgress -> GetPersonQueueStatusResponse(queueId, "InProgress")
            is PersonQueueStatus.Failure -> GetPersonQueueStatusResponse(queueId, "Failure", status.reason)
        }
    }

    @GetMapping("/{phone}")
    fun getPerson(
        @RequestHeader("Authorization") authToken: String,
        @PathVariable phone: Long
    ): Person {
        authService.checkToken(authToken)
        return personService.getPerson(phone)
    }

    @DeleteMapping("/{phone}")
    fun deletePerson(
        @RequestHeader("Authorization") authToken: String,
        @PathVariable phone: Long) {
        authService.checkToken(authToken)
        personService.deletePerson(phone)
    }
}