package com.craftandtechnology.dockerlocalhost.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile

@Configuration
@Import(AppConfig::class)
@Profile("!debug")
class LocalTestConfig