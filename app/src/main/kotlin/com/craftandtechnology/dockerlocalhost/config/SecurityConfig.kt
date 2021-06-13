package com.craftandtechnology.dockerlocalhost.config

import com.craftandtechnology.dockerlocalhost.config.RestConfig.Companion.ORDERS_PATH
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .authorizeExchange()
            .pathMatchers(ORDERS_PATH)
            .hasAuthority("SCOPE_read:orders")
            .anyExchange()
            .authenticated()
//            .and()
//            .cors() // TODO add mapping for CORS
            .and()
            .oauth2ResourceServer()
            .jwt()
        return http.build()
    }
}