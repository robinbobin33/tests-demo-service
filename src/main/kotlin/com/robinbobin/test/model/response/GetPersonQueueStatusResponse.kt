package com.robinbobin.test.model.response

import com.robinbobin.test.model.PersonQueueStatus

data class GetPersonQueueStatusResponse(
    val queueId: String,
    val status: PersonQueueStatus
)