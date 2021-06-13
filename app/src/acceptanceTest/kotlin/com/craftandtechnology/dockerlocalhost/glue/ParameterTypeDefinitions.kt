package com.craftandtechnology.dockerlocalhost.glue

import com.craftandtechnology.dockerlocalhost.model.OrderStatus
import com.craftandtechnology.dockerlocalhost.types.ORDER_STATUS_REGEX
import io.cucumber.java8.En

@Suppress("LeakingThis", "Unused")
class ParameterTypeDefinitions : En {
    init {
        ParameterType("OrderStatus", ORDER_STATUS_REGEX) { orderStatus: String ->
            OrderStatus.valueOf(orderStatus)
        }
    }
}