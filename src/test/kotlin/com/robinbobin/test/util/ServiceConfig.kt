package com.robinbobin.test.util

import com.robinbobin.test.cache.PersonsCache
import com.robinbobin.test.cache.PersonsQueueCache
import com.robinbobin.test.config.AuthConfig
import com.robinbobin.test.config.TestDataConfig
import com.robinbobin.test.service.auth.AuthService
import com.robinbobin.test.service.auth.PersonService
import java.util.*

object ServiceConfig {

    val defaultPerson = RandomGenerator.getRandomPerson()
    val authToken = UUID.randomUUID().toString()

    fun getAuthService(): AuthService {
        val config = AuthConfig()
        config.token = authToken

        return AuthService(config)
    }

    fun getPersonService(): PersonService {
        val config = TestDataConfig()
        config.phone = defaultPerson.phone.toString()
        config.fio = defaultPerson.fio
        config.birthDate = defaultPerson.birthDate

        val personsCache = PersonsCache()
        val personsQueueCache = PersonsQueueCache()

        return PersonService(config, personsCache, personsQueueCache)
    }
}