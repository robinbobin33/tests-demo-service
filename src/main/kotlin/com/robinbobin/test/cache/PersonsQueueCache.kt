package com.robinbobin.test.cache

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.robinbobin.test.model.QueueItem
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

@Component
class PersonsQueueCache  {

    private val queueCache: Cache<String, QueueItem> = CacheBuilder.newBuilder()
        .maximumSize(1_000_000L)
        .expireAfterWrite(1L, TimeUnit.DAYS)
        .build()

    fun put(queueId: String, item: QueueItem): Unit {
        queueCache.put(queueId,  item)
    }

    fun get(queueId: String) = queueCache.getIfPresent(queueId)

    fun remove(queueId: String) = queueCache.invalidate(queueId)

    fun getAll() = HashMap(queueCache.asMap()).values

}