package com.create.dockerlocalhost.config.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsAsyncClient
import java.net.URI.create

@Configuration
class SnsConfig {
    @Bean
    fun snsAsyncClient(
        @Value("\${cloud.aws.sns.endpoint}") snsEndpoint: String,
        awsRegion: Region
    ) = SnsAsyncClient.builder()
        .region(awsRegion)
        .endpointOverride(create(snsEndpoint))
        .build()
}