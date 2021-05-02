package com.create.dockerlocalhost.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Import

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(
    AwsConfig::class,
)
class AppConfig