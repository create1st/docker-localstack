package com.craftandtechnology.dockerlocalhost.glue

import com.craftandtechnology.dockerlocalhost.model.OrderStatus
import com.craftandtechnology.dockerlocalhost.model.TestOrder.order
import com.craftandtechnology.dockerlocalhost.repository.OrderRepository
import com.craftandtechnology.dockerlocalhost.types.ORDER_STATUS_REGEX
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono
import java.util.UUID.randomUUID

@Suppress("LeakingThis", "Unused")
class StepDefinitions @Autowired constructor(
    private val orderRepository: OrderRepository
) : En {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private lateinit var transactionId: String

    init {
        Before { scenario: Scenario ->
            logger.info { "Before ${scenario.name}" }
            transactionId = randomUUID().toString()
        }

        ParameterType("OrderStatus", ORDER_STATUS_REGEX) { orderStatus: String ->
            OrderStatus.valueOf(orderStatus)
        }

        Given("Order with status \"{OrderStatus}\"") { orderStatus: OrderStatus ->
            logger.info { "Order with $orderStatus" }
            val order = order(transactionId)
                .copy(orderStatus = orderStatus)
            val orderMono = Mono.just(order)

            orderRepository.save(orderMono)
        }

        When("Order status is Accepted") {
            logger.info { "Order status is Accepted" }

        }

        Then("Order is sent to execution") {
            logger.info { "Order is sent to execution" }
        }
    }
}