package com.craftandtechnology.dockerlocalhost.glue.config

import com.craftandtechnology.dockerlocalhost.config.AcceptanceTestConfig
import com.craftandtechnology.dockerlocalhost.config.DebugLocalTestConfig
import com.craftandtechnology.dockerlocalhost.config.LocalTestConfig
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT


@CucumberContextConfiguration
@SpringBootTest(
    classes = [
        DebugLocalTestConfig::class,
        LocalTestConfig::class,
        AcceptanceTestConfig::class,
    ],
    webEnvironment = DEFINED_PORT,
)
class GlueConfig