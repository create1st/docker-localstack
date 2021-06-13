package com.craftandtechnology.dockerlocalhost.config.auth0

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest
import org.springframework.security.oauth2.client.endpoint.ReactiveOAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.web.reactive.function.OAuth2BodyExtractors
import org.springframework.util.Assert
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.BodyInserters.FormInserter
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class Auth0WebClientReactiveAuthorizationCodeTokenResponseClient(
    private val audience: String
) : ReactiveOAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> {
    companion object {
        private const val AUTH0_AUDIENCE = "audience"
    }

    private val webClient: WebClient = WebClient.builder().build()

    override fun getTokenResponse(grantRequest: OAuth2ClientCredentialsGrantRequest): Mono<OAuth2AccessTokenResponse> {
        Assert.notNull(grantRequest, "grantRequest cannot be null")
        return Mono.defer { requestToken(grantRequest) }
    }

    private fun requestToken(grantRequest: OAuth2ClientCredentialsGrantRequest) =
        this.webClient.post()
            .uri(grantRequest.clientRegistration.providerDetails.tokenUri)
            .headers { headers -> populateTokenRequestHeaders(grantRequest, headers) }
            .body(createTokenRequestBody(grantRequest))
            .exchangeToMono { response -> readTokenResponse(grantRequest.clientRegistration, response) }

    private fun populateTokenRequestHeaders(grantRequest: OAuth2ClientCredentialsGrantRequest, headers: HttpHeaders) {
        val clientRegistration: ClientRegistration = grantRequest.clientRegistration
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC == clientRegistration.clientAuthenticationMethod
            || ClientAuthenticationMethod.BASIC == clientRegistration.clientAuthenticationMethod
        ) {
            headers.setBasicAuth(clientRegistration.clientId, clientRegistration.clientSecret)
        }
    }

    private fun createTokenRequestBody(grantRequest: OAuth2ClientCredentialsGrantRequest): FormInserter<String> {
        val body = BodyInserters.fromFormData(
            OAuth2ParameterNames.GRANT_TYPE,
            grantRequest.grantType.value
        )
        return populateTokenRequestBody(grantRequest.clientRegistration, body)
    }


    private fun populateTokenRequestBody(
        clientRegistration: ClientRegistration,
        body: FormInserter<String>
    ): FormInserter<String> {
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC != clientRegistration.clientAuthenticationMethod
            && ClientAuthenticationMethod.BASIC != clientRegistration.clientAuthenticationMethod
        ) {
            body.with(OAuth2ParameterNames.CLIENT_ID, clientRegistration.clientId)
        }
        if (ClientAuthenticationMethod.CLIENT_SECRET_POST == clientRegistration.clientAuthenticationMethod
            || ClientAuthenticationMethod.POST == clientRegistration.clientAuthenticationMethod
        ) {
            body.with(OAuth2ParameterNames.CLIENT_SECRET, clientRegistration.clientSecret)
        }
        val scopes: Set<String>? = clientRegistration.scopes
        if (!scopes.isNullOrEmpty()) {
            val scopesString = scopes.joinToString { " " }
            body.with(OAuth2ParameterNames.SCOPE, scopesString)
        }
        body.with(AUTH0_AUDIENCE, audience)
        return body
    }

    private fun readTokenResponse(
        clientRegistration: ClientRegistration,
        response: ClientResponse
    ) = response.body(OAuth2BodyExtractors.oauth2AccessTokenResponse())
        .map { tokenResponse -> populateTokenResponse(clientRegistration, tokenResponse) }

    private fun populateTokenResponse(
        clientRegistration: ClientRegistration,
        tokenResponse: OAuth2AccessTokenResponse
    ): OAuth2AccessTokenResponse {
        var tokenResponse = tokenResponse
        val scopes: Set<String>? = tokenResponse.accessToken.scopes
        if (scopes.isNullOrEmpty()) {
            val defaultScopes = clientRegistration.scopes
            tokenResponse = OAuth2AccessTokenResponse
                .withResponse(tokenResponse)
                .scopes(defaultScopes)
                .build()
        }
        return tokenResponse
    }

}