package com.create.dockerlocalhost.config.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.kms.KmsAsyncClient
import java.net.URI

@Configuration
class KmsConfig {
    @Bean
    fun kmsAsyncClient(
        @Value("\${cloud.aws.kms.endpoint}") kmsEndpoint: String,
        awsRegion: Region
    ) = KmsAsyncClient.builder()
        .region(awsRegion)
        .endpointOverride(URI.create(kmsEndpoint))
        .build()
}