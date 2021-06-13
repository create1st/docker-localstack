package com.craftandtechnology.dockerlocalhost.steps

import com.craftandtechnology.dockerlocalhost.config.RestConfig.Companion.ORDERS_PATH
import com.craftandtechnology.dockerlocalhost.config.RestAcceptanceTestConfig.Companion.AUTH0_CLIENT_REGISTRATION
import com.craftandtechnology.dockerlocalhost.state.StateHolder
import com.craftandtechnology.dockerlocalhost.model.Order
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId
import org.springframework.test.web.reactive.server.WebTestClient

class RestSteps(
    private val stateHolder: StateHolder,
    private val webTestClient: WebTestClient
) {
    fun whenOrderListIsRequestedOnAPICall() {
        stateHolder.result = webTestClient.get()
            .uri(ORDERS_PATH)
            .attributes(clientRegistrationId(AUTH0_CLIENT_REGISTRATION))
            .exchange()
            .expectStatus()
            .isOk
            .expectBodyList(Order::class.java)
            .returnResult()
            .responseBody
    }
}