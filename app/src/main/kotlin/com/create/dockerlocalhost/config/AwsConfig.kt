package com.create.dockerlocalhost.config

import com.create.dockerlocalhost.config.aws.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import software.amazon.awssdk.regions.Region

@Configuration
@Import(
    DynamoDbConfig::class,
    KmsConfig::class,
    SecretsManagerConfig::class,
    SnsConfig::class,
    SqsConfig::class,
)
class AwsConfig {
    @Bean
    fun awsRegion(@Value("\${cloud.aws.region.static}") region: String) = Region.of(region)
}