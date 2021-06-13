package com.craftandtechnology.dockerlocalhost.model

import com.craftandtechnology.dockerlocalhost.repository.dynamodb.converters.ZonedDateTimeAttributeConverter
import com.craftandtechnology.dockerlocalhost.time.TIMEZONE
import com.craftandtechnology.dockerlocalhost.time.ZONE_ID
import com.fasterxml.jackson.annotation.JsonFormat
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*
import java.time.ZonedDateTime

@DynamoDbBean
data class Order(
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("userId")
    var userId: String,
    @get:DynamoDbSortKey
    @get:DynamoDbSecondaryPartitionKey(indexNames = ["transactionIndex"])
    @get:DynamoDbAttribute("transactionId")
    var transactionId: String,
    @get:DynamoDbAttribute("orderStatus")
    var orderStatus: OrderStatus,
    @get:DynamoDbAttribute("timestamp")
    @get:DynamoDbConvertedBy(ZonedDateTimeAttributeConverter::class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSz", timezone = TIMEZONE)
    var timestamp: ZonedDateTime = ZonedDateTime.now(ZONE_ID)
)