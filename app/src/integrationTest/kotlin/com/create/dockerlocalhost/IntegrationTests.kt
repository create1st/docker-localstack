package com.create.dockerlocalhost


import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(
        classes = [App::class]
)
class IntegrationTests {

    @Test
    fun contextLoads() {
    }

}
