package com.create.dockerlocalhost.config.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerAsyncClient
import java.net.URI

@Configuration
class SecretsManagerConfig {
    @Bean
    fun secretsManagerAsyncClient(
        @Value("\${cloud.aws.secretsmanager.endpoint}") secretsManagerEndpoint: String,
        awsRegion: Region
    ) = SecretsManagerAsyncClient.builder()
        .region(awsRegion)
        .endpointOverride(URI.create(secretsManagerEndpoint))
        .build()
}