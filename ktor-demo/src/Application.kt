package com.joram

import arrow.core.Either
import arrow.core.right
import arrow.fx.coroutines.kotlinx.suspendCancellable
import com.sparetimedevs.pofpaf.handler.handle
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.routing
import io.ktor.util.pipeline.ContextDsl
import io.ktor.util.pipeline.PipelineContext
import io.ktor.routing.get as getWithoutCancelling

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }
    
    routing {
        get("/") {
            handle(
                logic = {
                    val domainLogic: Either<DomainError, String> = "HELLO WORLD!".right()
                    domainLogic
                },
                ifSuccess = { a -> handleSuccessTextPlain(call, a) },
                ifDomainError = { e -> handleDomainError(call, ::log, e) },
                ifSystemFailure = { throwable -> handleSystemFailure(call, ::log, throwable) },
                ifUnrecoverableState = ::log
            )
        }
        
        get("/json/gson") {
            handle(
                logic = {
                    val domainLogic: Either<DomainError, Map<String, String>> = mapOf("hello" to "world").right()
                    domainLogic
                },
                ifSuccess = { a -> handleSuccess(call, a) },
                ifDomainError = { e -> handleDomainError(call, ::log, e) },
                ifSystemFailure = { throwable -> handleSystemFailure(call, ::log, throwable) },
                ifUnrecoverableState = ::log
            )
        }
    }
}

data class DomainError(val errorMessage: String)

suspend fun handleSuccessTextPlain(
    call: ApplicationCall,
    text: String
): Either<Throwable, Unit> =
    Either.catch {
        call.respondText(text, contentType = ContentType.Text.Plain)
    }

suspend fun <A : Any> handleSuccess(
    call: ApplicationCall,
    a: A
): Either<Throwable, Unit> =
    Either.catch {
        call.respond(status = HttpStatusCode.OK, message = a)
    }

suspend fun <E> handleDomainError(
    call: ApplicationCall,
    log: suspend (e: E) -> Either<Throwable, Unit>,
    e: E
): Either<Throwable, Unit> =
    Either.catch {
        log(e)
        call.respond(status = HttpStatusCode.InternalServerError, message = "Something went wrong. Sorry!")
    }

suspend fun handleSystemFailure(
    call: ApplicationCall,
    log: suspend (throwable: Throwable) -> Either<Throwable, Unit>,
    throwable: Throwable
): Either<Throwable, Unit> =
    Either.catch {
        log(throwable)
        call.respond(status = HttpStatusCode.InternalServerError, message = "Something went wrong. Sorry!")
    }

private suspend fun <A> log(a: A): Either<Throwable, Unit> =
    Unit.right() // Should implement logging.

@JvmName("getCancellable")
@ContextDsl
fun <A> Route.get(path: String, body: suspend PipelineContext<Unit, ApplicationCall>.() -> A): Route =
    getWithoutCancelling(path) { suspendCancellable { body() } }
