package com.create.dockerlocalhost.repository.dynamodb

import com.create.dockerlocalhost.model.Order
import com.create.dockerlocalhost.repository.OrderRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable

@Repository
class DynamoDbOrderRepository(private val table: DynamoDbAsyncTable<Order>): OrderRepository {

    override fun save(order: Mono<Order>) = order
        .map(table::putItem)
        .flatMap { Mono.fromFuture(it) }
        .then(order)
}