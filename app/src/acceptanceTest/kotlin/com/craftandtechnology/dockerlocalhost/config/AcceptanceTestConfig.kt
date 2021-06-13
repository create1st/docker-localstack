package com.craftandtechnology.dockerlocalhost.config

import com.craftandtechnology.dockerlocalhost.state.StateHolder
import com.craftandtechnology.dockerlocalhost.steps.DynamoDbSteps
import com.craftandtechnology.dockerlocalhost.steps.RestSteps
import com.craftandtechnology.dockerlocalhost.model.Order
import com.craftandtechnology.dockerlocalhost.repository.OrderRepository
import com.craftandtechnology.dockerlocalhost.steps.StateValidationSteps
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable

@Import(RestAcceptanceTestConfig::class)
@Configuration
class AcceptanceTestConfig {

    @Bean
    fun stateHolder() = StateHolder()

    @Bean
    fun dynamoDbSteps(
        stateHolder: StateHolder,
        orderRepository: OrderRepository,
        table: DynamoDbAsyncTable<Order>,
    ) = DynamoDbSteps(
        stateHolder,
        orderRepository,
        table
    )

    @Bean
    fun restSteps(
        stateHolder: StateHolder,
        webTestClient: WebTestClient,
    ) = RestSteps(
        stateHolder,
        webTestClient,
    )

    @Bean
    fun stateValidationSteps(
        stateHolder: StateHolder
    ) = StateValidationSteps(stateHolder)
}