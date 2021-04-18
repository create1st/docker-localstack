package com.create.dockerlocalhost

import com.create.dockerlocalhost.config.AppConfig
import io.cucumber.java8.En
import io.cucumber.spring.CucumberContextConfiguration
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest

@Suppress("LeakingThis")
@CucumberContextConfiguration
@SpringBootTest(
    classes = [AppConfig::class]
)
class StepDefinitions : En {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    init {
        Given("App is running"){
            logger.info { "App is running" }
        }

        When("New message") {
            logger.info { "New message" }
        }

        Then("Message should be persisted") {
            logger.info { "Message should be persisted" }
        }
    }
}