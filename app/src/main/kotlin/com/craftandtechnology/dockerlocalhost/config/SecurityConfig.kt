package com.craftandtechnology.dockerlocalhost.config

import com.craftandtechnology.dockerlocalhost.config.RestConfig.Companion.ORDERS_PATH
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    companion object {
        private val logger = KotlinLogging.logger {}
        private const val ALL_METHODS = "*"
        private const val ALL_HEADERS = "*"
    }

    @Bean
    fun corsConfiguration(@Value("\${application.rest.cors.access-control-allow-origin:}") accessControlAllowOrigin: String): CorsConfiguration {
        logger.info { "Setting CORS for: $accessControlAllowOrigin" }
        val config = CorsConfiguration()
        if (accessControlAllowOrigin.isNotEmpty()) {
            config.allowCredentials = true
            config.addAllowedOrigin(accessControlAllowOrigin)
            config.addAllowedHeader(ALL_HEADERS)
            config.addAllowedMethod(ALL_METHODS)
        }
        return config
    }

    @Bean
    fun corsConfigurationSource(corsConfiguration: CorsConfiguration): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        if (corsConfiguration.allowedOrigins != null) {
            source.registerCorsConfiguration(ORDERS_PATH, corsConfiguration)
        }
        return source
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .authorizeExchange()
            .pathMatchers(ORDERS_PATH)
            .hasAuthority("SCOPE_read:orders")
            .anyExchange()
            .authenticated()
            .and()
            .cors()
            .and()
            .oauth2ResourceServer()
            .jwt()
        return http.build()
    }
}