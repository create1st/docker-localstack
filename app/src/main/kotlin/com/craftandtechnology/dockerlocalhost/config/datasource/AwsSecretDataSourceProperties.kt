package com.craftandtechnology.dockerlocalhost.config.datasource

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.datasource.aws")
class AwsSecretDataSourceProperties {
    var secret: String? = null
}