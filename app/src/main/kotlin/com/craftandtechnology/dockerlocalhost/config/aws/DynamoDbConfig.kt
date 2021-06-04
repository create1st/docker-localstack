package com.craftandtechnology.dockerlocalhost.config.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI.create

@Configuration
class DynamoDbConfig {
    @Bean
    fun dynamoDbAsyncClient(
        @Value("\${cloud.aws.dynamodb.endpoint}") dynamoDbEndpoint: String,
        awsRegion: Region
    ) = DynamoDbAsyncClient.builder()
        .region(awsRegion)
        .endpointOverride(create(dynamoDbEndpoint))
        .build()

    @Bean
    fun dynamoDbEnhancedAsyncClient(
        dynamoDbAsyncClient: DynamoDbAsyncClient
    ) = DynamoDbEnhancedAsyncClient.builder()
        .dynamoDbClient(dynamoDbAsyncClient)
        .build()
}