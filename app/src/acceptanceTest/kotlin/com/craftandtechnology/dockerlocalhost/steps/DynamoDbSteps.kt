package com.craftandtechnology.dockerlocalhost.steps

import com.craftandtechnology.dockerlocalhost.state.StateHolder
import com.craftandtechnology.dockerlocalhost.model.Order
import com.craftandtechnology.dockerlocalhost.model.OrderStatus
import com.craftandtechnology.dockerlocalhost.model.TestOrder.order
import com.craftandtechnology.dockerlocalhost.repository.OrderRepository
import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.model.Page
import java.util.concurrent.CompletableFuture

class DynamoDbSteps(
    private val stateHolder: StateHolder,
    private val orderRepository: OrderRepository,
    private val table: DynamoDbAsyncTable<Order>
) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun clearTable(): List<Order> =
        Flux.from(table.scan())
            .flatMapIterable(Page<Order>::items)
            .map<CompletableFuture<Order>>(table::deleteItem)
            .flatMap { Mono.fromFuture(it) }
            .collectList()
            .toFuture()
            .get()

    fun givenOrderWithStatus(orderStatus: OrderStatus) {
        logger.info { "Order with $orderStatus" }
        val order = order(stateHolder.transactionId)
            .copy(orderStatus = orderStatus)
        saveOrder(order)
    }

    fun givenOrder() {
        logger.info { "Order" }
        val order = order(stateHolder.transactionId)
        saveOrder(order)
    }

    private fun saveOrder(order: Order) =
        Mono.just(order)
            .`as`(orderRepository::save)
            .toFuture()
            .get()
}