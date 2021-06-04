package com.craftandtechnology.dockerlocalhost.glue.config

import com.craftandtechnology.dockerlocalhost.config.AppConfig
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest(
    classes = [AppConfig::class]
)
class GlueConfig