package com.create.dockerlocalhost

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith

@CucumberOptions(
    plugin = ["pretty"],
    features = ["src/acceptanceTest/resources/features"],
    glue = ["com.create.dockerlocalhost.glue.config", "com.create.dockerlocalhost.glue"],
    tags = "not @ignore",
    publish = false,
    )
@RunWith(Cucumber::class)
class AcceptanceTests