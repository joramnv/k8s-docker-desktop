package com.joram

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.joram.model.WhoIsRequest
import com.joram.model.WhoIsResponse
import io.dapr.client.DaprClientBuilder
import io.dapr.client.domain.HttpExtension

object InvokeClient {

    /**
     * Identifier in Dapr for the service this client will invoke.
     */
    private const val APP_B_APP_ID = "app-b"

    fun invokeServiceB(whoIsRequest: WhoIsRequest): Either<DomainError, WhoIsResponse> {
        val whoIsResponse: WhoIsResponse? = DaprClientBuilder().build().use { client ->
            client.invokeMethod(APP_B_APP_ID, "who", whoIsRequest, HttpExtension.POST, mapOf(CONTENT_TYPE to APPLICATION_JSON), WhoIsResponse::class.java).block()
        }
        return whoIsResponse?.right() ?: DomainError("Something went wrong, there is no WhoIsResponse").left()
    }
}
