package com.create.dockerlocalhost

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@CucumberOptions(
    plugin = ["pretty"],
    features = ["src/test/resources/features"],
    tags = "not @ignore",
)
@RunWith(Cucumber::class)
class AcceptanceTests