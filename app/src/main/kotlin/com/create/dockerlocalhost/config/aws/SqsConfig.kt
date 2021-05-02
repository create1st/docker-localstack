package com.create.dockerlocalhost.config.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.net.URI.create

@Configuration
class SqsConfig {
    @Bean
    fun sqsAsyncClient(
        @Value("\${cloud.aws.sqs.endpoint}") sqsEndpoint: String,
        awsRegion: Region
    ) = SqsAsyncClient.builder()
        .region(awsRegion)
        .endpointOverride(create(sqsEndpoint))
        .build()
}