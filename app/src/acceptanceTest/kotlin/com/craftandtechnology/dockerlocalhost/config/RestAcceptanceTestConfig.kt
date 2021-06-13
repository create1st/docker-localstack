package com.craftandtechnology.dockerlocalhost.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.*
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest
import org.springframework.security.oauth2.client.endpoint.ReactiveOAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.test.web.reactive.server.WebTestClient
import com.craftandtechnology.dockerlocalhost.config.auth0.Auth0WebClientReactiveAuthorizationCodeTokenResponseClient


@Configuration
class RestAcceptanceTestConfig {
    companion object {
        const val AUTH0_CLIENT_REGISTRATION = "auth0"
    }

    @Bean
    fun auth0WebClientReactiveAuthorizationCodeTokenResponseClient(@Value("\${auth0.audience}") audience: String) =
        Auth0WebClientReactiveAuthorizationCodeTokenResponseClient(audience)

    @Bean
    fun clientCredentialsReactiveOAuth2AuthorizedClientProvider(
        auth0WebClientReactiveAuthorizationCodeTokenResponseClient: ReactiveOAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest>
    ): ClientCredentialsReactiveOAuth2AuthorizedClientProvider {
        val clientCredentialsReactiveOAuth2AuthorizedClientProvider =
            ClientCredentialsReactiveOAuth2AuthorizedClientProvider()
        clientCredentialsReactiveOAuth2AuthorizedClientProvider.setAccessTokenResponseClient(auth0WebClientReactiveAuthorizationCodeTokenResponseClient)
        return clientCredentialsReactiveOAuth2AuthorizedClientProvider
    }


    @Bean
    fun authorizedClientServiceReactiveOAuth2AuthorizedClientManager(
        clientRegistrations: ReactiveClientRegistrationRepository,
        authorizedClientService: ReactiveOAuth2AuthorizedClientService,
        clientCredentialsReactiveOAuth2AuthorizedClientProvider: ReactiveOAuth2AuthorizedClientProvider
    ): AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager {
        val authorizedClientManager = AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
            clientRegistrations,
            authorizedClientService
        )
        authorizedClientManager.setAuthorizedClientProvider(clientCredentialsReactiveOAuth2AuthorizedClientProvider)
        return authorizedClientManager
    }

    @Bean
    fun auth0(
        authorizedClientManager: AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager,
    ): ServerOAuth2AuthorizedClientExchangeFilterFunction {
        val oauth = ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
        oauth.setDefaultClientRegistrationId(AUTH0_CLIENT_REGISTRATION)
        return oauth
    }

    @Bean
    fun webTestClient(
        @Value("\${craftandtechnology.rest.endpoint}") restEndpoint: String,
        auth0: ServerOAuth2AuthorizedClientExchangeFilterFunction
    ) = WebTestClient.bindToServer()
        .baseUrl(restEndpoint)
        .filter(auth0)
        .build()
}