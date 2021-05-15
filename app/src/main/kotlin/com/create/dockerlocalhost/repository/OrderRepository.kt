package com.create.dockerlocalhost.repository

import com.create.dockerlocalhost.model.Order
import reactor.core.publisher.Mono

interface  OrderRepository {
    fun save(order: Mono<Order>): Mono<Order>
}