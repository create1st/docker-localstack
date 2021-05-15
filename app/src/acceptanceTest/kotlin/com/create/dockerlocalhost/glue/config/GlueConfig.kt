package com.create.dockerlocalhost.glue.config

import com.create.dockerlocalhost.config.AppConfig
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest(
    classes = [AppConfig::class]
)
class GlueConfig