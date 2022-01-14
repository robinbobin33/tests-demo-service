package com.robinbobin.test.cache

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.robinbobin.test.model.Person
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

@Component
class PersonsCache  {

    private val personsCache: Cache<Long, Person> = CacheBuilder.newBuilder()
        .maximumSize(1_000_000L)
        .expireAfterWrite(1L, TimeUnit.DAYS)
        .build()

    fun put(personPhone: Long, person: Person): Unit =
            personsCache.put(personPhone,  person)

    fun get(personId: Long) = personsCache.getIfPresent(personId)

    fun remove(personId: Long) = personsCache.invalidate(personId)

    fun getAll() = HashMap(personsCache.asMap()).values

}