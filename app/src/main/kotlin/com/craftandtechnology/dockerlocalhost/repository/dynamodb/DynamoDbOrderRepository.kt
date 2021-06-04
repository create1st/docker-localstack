package com.craftandtechnology.dockerlocalhost.repository.dynamodb

import com.craftandtechnology.dockerlocalhost.model.Order
import com.craftandtechnology.dockerlocalhost.repository.OrderRepository
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@Repository
class DynamoDbOrderRepository(private val table: DynamoDbAsyncTable<Order>): OrderRepository {

    override fun save(order: Mono<Order>) = order
        .map(table::putItem)
        .flatMap { Mono.fromFuture(it) }
        .then(order)

    override fun getAllOrders() = Flux
        .from(table.scan())
        .flatMapIterable(Page<Order>::items)
}