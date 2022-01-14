package com.robinbobin.test.model

sealed class PersonQueueStatus {
    data class Failure(val reason: String): PersonQueueStatus()
    object Success: PersonQueueStatus()
    object InProgress: PersonQueueStatus()
}
