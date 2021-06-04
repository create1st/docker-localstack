package com.craftandtechnology.dockerlocalhost.repository

import com.craftandtechnology.dockerlocalhost.model.Order
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OrderRepository {
    fun save(order: Mono<Order>): Mono<Order>
    fun getAllOrders(): Flux<Order>
}