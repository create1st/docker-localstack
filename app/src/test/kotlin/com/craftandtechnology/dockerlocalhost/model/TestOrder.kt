package com.craftandtechnology.dockerlocalhost.model

import com.craftandtechnology.dockerlocalhost.time.ZONE_ID
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID.randomUUID

object TestOrder {
    val USER_ID: String = randomUUID().toString()
    val TRANSACTION_ID: String = randomUUID().toString()
    val TIMESTAMP: ZonedDateTime = ZonedDateTime.of(2021, 2, 1, 5, 10, 15, 20, ZONE_ID)

    fun order(transactionId: String = TRANSACTION_ID) = Order(
        userId = USER_ID,
        transactionId = transactionId,
        orderStatus = OrderStatus.New,
        timestamp = TIMESTAMP
    )
}