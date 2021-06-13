package com.craftandtechnology.dockerlocalhost.steps

import com.craftandtechnology.dockerlocalhost.state.StateHolder
import com.craftandtechnology.dockerlocalhost.model.TestOrder.order
import org.assertj.core.api.Assertions.assertThat

class StateValidationSteps(private val stateHolder: StateHolder) {
    fun thenResultContainsOrder() {
        val order = order(stateHolder.transactionId)
        assertThat(stateHolder.result)
            .asList()
            .containsOnly(order)
    }
}