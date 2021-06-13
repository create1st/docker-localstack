package com.craftandtechnology.dockerlocalhost.glue

import com.craftandtechnology.dockerlocalhost.state.StateHolder
import com.craftandtechnology.dockerlocalhost.steps.DynamoDbSteps
import com.craftandtechnology.dockerlocalhost.steps.RestSteps
import com.craftandtechnology.dockerlocalhost.model.OrderStatus
import com.craftandtechnology.dockerlocalhost.steps.StateValidationSteps
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired

@Suppress("LeakingThis", "Unused")
class StepDefinitions @Autowired constructor(
    private val stateHolder: StateHolder,
    private val dynamoDbSteps: DynamoDbSteps,
    private val restSteps: RestSteps,
    private val stateValidationSteps: StateValidationSteps,
) : En {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private lateinit var transactionId: String

    init {
        Before { scenario: Scenario ->
            logger.info { "Before ${scenario.name}" }
            stateHolder.init()
            dynamoDbSteps.clearTable()
        }

        Given("Order") {
            dynamoDbSteps.givenOrder()
        }

        Given("Order with status \"{OrderStatus}\"") { orderStatus: OrderStatus ->
            dynamoDbSteps.givenOrderWithStatus(orderStatus)
        }

        When("Order status is Accepted") {
            logger.info { "Order status is Accepted" }
        }

        When("I request orders list from API") {
            restSteps.whenOrderListIsRequestedOnAPICall()
        }

        Then("Order is sent to execution") {
        }

        Then("Returned list contains order") {
            stateValidationSteps.thenResultContainsOrder()
        }

    }
}