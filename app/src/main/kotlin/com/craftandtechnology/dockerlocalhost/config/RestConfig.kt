package com.craftandtechnology.dockerlocalhost.config

import com.craftandtechnology.dockerlocalhost.repository.OrderRepository
import com.craftandtechnology.dockerlocalhost.rest.GetAllOrdersHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.server.RouterFunctions.route

@Configuration
@EnableWebFlux
class RestConfig {
    companion object {
        private const val ORDERS_PATH = "/orders"
    }

    @Bean
    fun getAllOrdersHandler(orderRepository: OrderRepository) = GetAllOrdersHandler(orderRepository)

    @Bean
    fun getAllEmployeesRoute(getAllOrdersHandler: GetAllOrdersHandler) = route()
        .GET(ORDERS_PATH, getAllOrdersHandler::getAllOrders)
        .build()

}