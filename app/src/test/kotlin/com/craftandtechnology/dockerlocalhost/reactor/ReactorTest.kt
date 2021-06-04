package com.craftandtechnology.dockerlocalhost.reactor

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class ReactorTest {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @Test
    fun testOnError() {
        val flux = testFlux()
            .flatMap(this::sideJob)

        StepVerifier.create(flux)
            .expectNext("a")
            .verifyError()
    }

    @Test
    fun testContinueOnError() {
        val flux = testFlux()
            .flatMap(this::sideJob)
            .onErrorContinue { ex, o -> logger.error(ex) { "Failed on $o" } }

        StepVerifier.create(flux)
            .expectNext("a")
            .expectNext("b")
            .expectNext("c")
            .expectNext("d")
            .verifyComplete()
    }

    @Test
    fun testContinueOnErrorMono() {
        val flux = testFlux()
            .map{ Mono.just(it) }
            .flatMap(this::monoSideJob)
            .onErrorContinue { ex, o -> logger.error(ex) { "Failed on $o" } }

        StepVerifier.create(flux)
            .expectNext("a")
            .expectNext("b")
            .expectNext("c")
            .expectNext("d")
            .verifyComplete()
    }

    @Test
    fun testContinueOnErrorFlux() {
        val flux = testFlux()
            .`as`(this::fluxSideJob)
            .onErrorContinue { ex, o -> logger.error(ex) { "Failed on $o" } }

        StepVerifier.create(flux)
            .expectNext("a")
            .expectNext("b")
            .expectNext("c")
            .expectNext("d")
            .verifyComplete()
    }

    private fun testFlux() = Flux.just("a", "b", "c", "d")

    private fun fluxSideJob(flux: Flux<String>) =
        flux.map { Mono.just(it) }
            .flatMap(this::monoSideJob)

    private fun monoSideJob(text: Mono<String>) =
        text
            .map(this::printOrFail)
            .then(text)


    private fun sideJob(text: String) =
        Mono.just(text)
            .map(this::printOrFail)
            .then(Mono.just(text))


    private fun printOrFail(text: String) {
        if (text == "b") {
            throw IllegalArgumentException()
        }
        logger.info { "Found $text" }
    }
}