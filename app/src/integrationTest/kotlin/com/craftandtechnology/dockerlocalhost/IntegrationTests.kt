package com.craftandtechnology.dockerlocalhost


import com.craftandtechnology.dockerlocalhost.config.AppConfig
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(
    classes = [AppConfig::class]
)
class IntegrationTests {

    @Test
    fun contextLoads() {
    }

}
