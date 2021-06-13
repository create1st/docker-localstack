package com.craftandtechnology.dockerlocalhost.glue.config

import com.craftandtechnology.dockerlocalhost.config.AcceptanceTestConfig
import com.craftandtechnology.dockerlocalhost.config.AppConfig
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT


@CucumberContextConfiguration
@SpringBootTest(
    classes = [AppConfig::class, AcceptanceTestConfig::class],
    webEnvironment = DEFINED_PORT,
)
class GlueConfig