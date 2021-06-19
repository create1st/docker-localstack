package com.craftandtechnology.dockerlocalhost.config

import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile


@Configuration
@EnableAutoConfiguration(
    exclude = [
        WebEndpointAutoConfiguration::class
    ]
)
@Import(
    AwsConfig::class,
    DataSourceConfig::class,
    RepositoryConfig::class,
)
@Profile("debug")
class DebugLocalTestConfig