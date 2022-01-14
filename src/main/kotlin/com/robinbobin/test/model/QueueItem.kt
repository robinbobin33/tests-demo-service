package com.robinbobin.test.model

data class QueueItem(
        val id: String,
        val status: PersonQueueStatus,
        val person: Person
)