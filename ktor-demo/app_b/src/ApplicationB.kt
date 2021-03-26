package com.joram

import arrow.core.Either
import arrow.core.right
import com.joram.model.WhoIsRequest
import com.joram.model.WhoIsResponse
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.moduleB(testing: Boolean = false) {

    routing {
        get("/") {
            Either.resolve(
                f = {
                    val domainLogic: Either<DomainError, String> = "HELLO WORLD!".right()
                    domainLogic
                },
                success = { a -> handleSuccessTextPlain(call, a) },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }

        get("/json/gson") {
            Either.resolve(
                f = {
                    val domainLogic: Either<DomainError, Map<String, String>> = mapOf("hello" to "world").right()
                    domainLogic
                },
                success = { a -> handleSuccess(call, a) },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }

        get("/who") {
            Either.resolve(
                f = { InvokeClient.invokeServiceA(WhoIsRequest("app-b")) },
                success = { a -> handleSuccess(call, a) },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }

        post("/who") {
            Either.resolve(
                f = {
                    val thisService = "app-b"
                    val whoIsRequest = call.receive<WhoIsRequest>()
                    val whoIsResponse = WhoIsResponse(
                        requestFrom = whoIsRequest.requestFrom,
                        responseFrom = thisService,
                        message = "Hello ${whoIsRequest.requestFrom}, this is a message from $thisService"
                    )
                    whoIsResponse.right()
                },
                success = { a -> handleSuccess(call, a) },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }
    }
}
