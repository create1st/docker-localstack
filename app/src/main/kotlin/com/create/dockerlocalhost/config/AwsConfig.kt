package com.create.dockerlocalhost.config

import com.create.dockerlocalhost.config.aws.DynamoDbConfig
import com.create.dockerlocalhost.config.aws.KmsConfig
import com.create.dockerlocalhost.config.aws.SnsConfig
import com.create.dockerlocalhost.config.aws.SqsConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import software.amazon.awssdk.regions.Region

@Configuration
@Import(
    DynamoDbConfig::class,
    KmsConfig::class,
    SnsConfig::class,
    SqsConfig::class,
)
class AwsConfig {
    @Bean
    fun awsRegion(@Value("\${cloud.aws.region.static}") region: String) = Region.of(region)
}