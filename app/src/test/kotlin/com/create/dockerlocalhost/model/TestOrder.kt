package com.create.dockerlocalhost.model

import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID.randomUUID

object TestOrder {
    val USER_ID: String = randomUUID().toString()
    val TRANSACTION_ID: String = randomUUID().toString()
    val TIMESTAMP: ZonedDateTime = ZonedDateTime.of(2021, 2, 1, 5, 10, 15, 20, ZoneId.systemDefault())

    fun order(transactionId: String = TRANSACTION_ID) = Order(
        userId = USER_ID,
        transactionId = transactionId,
        orderStatus = OrderStatus.New,
        timestamp = TIMESTAMP
    )
}