package com.craftandtechnology.dockerlocalhost

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith

@CucumberOptions(
    plugin = ["pretty"],
    features = ["src/acceptanceTest/resources/features"],
    glue = ["com.craftandtechnology.dockerlocalhost.glue.config", "com.craftandtechnology.dockerlocalhost.glue"],
    tags = "not @ignore",
    publish = false,
    )
@RunWith(Cucumber::class)
class AcceptanceTests