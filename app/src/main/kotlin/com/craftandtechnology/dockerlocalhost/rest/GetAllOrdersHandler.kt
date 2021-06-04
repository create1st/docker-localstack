package com.craftandtechnology.dockerlocalhost.rest

import com.craftandtechnology.dockerlocalhost.model.Order
import com.craftandtechnology.dockerlocalhost.repository.OrderRepository
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class GetAllOrdersHandler(private val orderRepository: OrderRepository) {

    @SuppressWarnings("unused")
    fun getAllOrders(request: ServerRequest): Mono<ServerResponse> = orderRepository
        .getAllOrders()
        .`as`(this::responseOk)

    private fun responseOk(orders: Flux<Order>) = ServerResponse
        .ok()
        .body(orders, Order::class.java)
}