package com.craftandtechnology.dockerlocalhost.config

import com.craftandtechnology.dockerlocalhost.model.Order
import com.craftandtechnology.dockerlocalhost.repository.dynamodb.DynamoDbOrderRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Configuration
class RepositoryConfig {
    companion object {
        const val ORDER_TABLE = "docker-localstack-order-table"
    }

    @Bean
    fun orderTable(dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient) = dynamoDbEnhancedAsyncClient
        .table(ORDER_TABLE, TableSchema.fromBean(Order::class.java))

    @Bean
    fun orderRepository(orderTable: DynamoDbAsyncTable<Order>) = DynamoDbOrderRepository(orderTable)
}