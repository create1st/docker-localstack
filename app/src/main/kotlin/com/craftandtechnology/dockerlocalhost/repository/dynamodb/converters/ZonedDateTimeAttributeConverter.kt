package com.craftandtechnology.dockerlocalhost.repository.dynamodb.converters

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.ZonedDateTime

class ZonedDateTimeAttributeConverter : AttributeConverter<ZonedDateTime> {
    override fun transformFrom(input: ZonedDateTime): AttributeValue = AttributeValue.builder()
        .s(input.toString())
        .build()

    override fun transformTo(input: AttributeValue?) =
        if (input != null) ZonedDateTime.parse(input.s()) else null

    override fun type(): EnhancedType<ZonedDateTime> = EnhancedType.of(ZonedDateTime::class.java)

    override fun attributeValueType() = AttributeValueType.S
}