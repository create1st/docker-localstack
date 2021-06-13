package com.craftandtechnology.dockerlocalhost.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Import

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(
    AwsConfig::class,
    DataSourceConfig::class,
    RepositoryConfig::class,
    RestConfig::class,
    SecurityConfig::class
)
class AppConfig