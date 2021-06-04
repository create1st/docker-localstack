package com.craftandtechnology.dockerlocalhost.config

import com.craftandtechnology.dockerlocalhost.config.datasource.AwsSecretDataSourceProperties
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.configurationprocessor.json.JSONException
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DatabaseDriver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertiesPropertySource
import software.amazon.awssdk.services.secretsmanager.SecretsManagerAsyncClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import java.util.*

@EnableConfigurationProperties(AwsSecretDataSourceProperties::class)
@Configuration
class DataSourceConfig {
    companion object {
        private const val AWS_DATA_SOURCE_PROPERTIES = "aws-datasource-properties"
        private const val AWS_SECRET_USERNAME = "username"
        private const val AWS_SECRET_PASSWORD = "password"
        private const val AWS_SECRET_HOST = "host"
        private const val AWS_SECRET_PORT = "port"
        private const val AWS_SECRET_DATABASE_NAME = "dbname"

        private val EMPTY_PROPERTIES = Properties()

        private val logger = KotlinLogging.logger {}
    }

    @Bean
    @Primary
    fun customDataSourceProperties(
        secretsManagerAsyncClient: SecretsManagerAsyncClient,
        environment: ConfigurableEnvironment,
        awsSecretDataSourceProperties: AwsSecretDataSourceProperties,
        dataSourceProperties: DataSourceProperties
    ): DataSourceProperties {
        val properties = findAwsDataSourceProperties(
            secretsManagerAsyncClient,
            awsSecretDataSourceProperties,
            dataSourceProperties
        )
        if (properties.isEmpty) {
            logger.info("No AWS credentials secret was configured. Falling back to properties set for username/password.")
        } else {
            val propertiesPropertySource = PropertiesPropertySource(AWS_DATA_SOURCE_PROPERTIES, properties)
            environment.getPropertySources()
                .addFirst(propertiesPropertySource)
        }
        return dataSourceProperties
    }

    private fun findAwsDataSourceProperties(
        secretsManagerAsyncClient: SecretsManagerAsyncClient,
        awsSecretDataSourceProperties: AwsSecretDataSourceProperties,
        dataSourceProperties: DataSourceProperties
    ): Properties {
        if (!awsSecretDataSourceProperties.secret.isNullOrEmpty()) {
            return getAwsDataSourceProperties(
                secretsManagerAsyncClient,
                awsSecretDataSourceProperties,
                dataSourceProperties
            )
        }
        return EMPTY_PROPERTIES
    }

    private fun getAwsDataSourceProperties(
        secretsManagerAsyncClient: SecretsManagerAsyncClient,
        awsSecretDataSourceProperties: AwsSecretDataSourceProperties,
        dataSourceProperties: DataSourceProperties
    ): Properties {
        try {
            return getAwsSecretDataSourceProperties(
                secretsManagerAsyncClient,
                awsSecretDataSourceProperties,
                dataSourceProperties
            )
        } catch (e: JSONException) {
            logger.error("Datasource AWS secret property was set but an error occurred parsing the value")
        }
        return EMPTY_PROPERTIES
    }

    private fun getAwsSecretDataSourceProperties(
        secretsManagerAsyncClient: SecretsManagerAsyncClient,
        awsSecretDataSourceProperties: AwsSecretDataSourceProperties,
        dataSourceProperties: DataSourceProperties
    ): Properties {
        val dbCredentials = getSecretJson(secretsManagerAsyncClient, awsSecretDataSourceProperties.secret)
        val driverClassName = dataSourceProperties.driverClassName
        val username = dataSourceProperties.username ?: dbCredentials.getString(AWS_SECRET_USERNAME)
        val password = dataSourceProperties.password ?: dbCredentials.getString(AWS_SECRET_PASSWORD)
        val host = dbCredentials.getString(AWS_SECRET_HOST)
        val port = dbCredentials.getString(AWS_SECRET_PORT)
        val dbname = dbCredentials.getString(AWS_SECRET_DATABASE_NAME)
        val databaseDriver = findDatabaseDriver(driverClassName)
        val url = dataSourceProperties.url ?: buildUrl(databaseDriver, host, port, dbname)
        return buildDataSourceProperties(driverClassName, username, password, url)
    }

    private fun getSecretJson(
        secretsManagerAsyncClient: SecretsManagerAsyncClient,
        secretId: String?
    ): JSONObject {
        val getSecretValueRequest = GetSecretValueRequest.builder()
            .secretId(secretId)
            .build()
        val secretResponse = secretsManagerAsyncClient.getSecretValue(getSecretValueRequest)
            .get()
        return JSONObject(secretResponse.secretString())
    }

    private fun buildDataSourceProperties(
        driverClassName: String,
        username: String,
        password: String,
        url: String
    ): Properties {
        val properties = Properties()
        properties.setProperty("spring.datasource.driverClassName", driverClassName)
        properties.setProperty("spring.datasource.username", username)
        properties.setProperty("spring.datasource.password", password)
        properties.setProperty("spring.datasource.url", url)
        return properties
    }

    private fun findDatabaseDriver(driverClassName: String) =
        DatabaseDriver.values().find { it.driverClassName == driverClassName }!!

    private fun buildUrl(
        databaseDriver: DatabaseDriver,
        host: String,
        port: String,
        dbName: String
    ) = "jdbc:${databaseDriver.name.lowercase(Locale.ENGLISH)}://$host:$port/$dbName"
}


