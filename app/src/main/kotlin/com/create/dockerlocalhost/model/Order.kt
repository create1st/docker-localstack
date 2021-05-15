package com.create.dockerlocalhost.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*
import java.time.ZonedDateTime

@DynamoDbBean
data class Order(
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("userId")
    val userId: String,
    @get:DynamoDbSortKey
    @get:DynamoDbSecondaryPartitionKey(indexNames = ["transactionIndex"])
    @get:DynamoDbAttribute("transactionId")
    val transactionId: String,
    @get:DynamoDbAttribute("orderStatus")
    val orderStatus: OrderStatus,
    @get:DynamoDbAttribute("timestamp")
    val timestamp: ZonedDateTime = ZonedDateTime.now()
)