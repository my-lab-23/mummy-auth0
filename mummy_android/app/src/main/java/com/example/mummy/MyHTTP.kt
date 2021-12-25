package com.example.mummy

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.net.ConnectException
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.engine.cio.*

object MyHTTP {

    val client = HttpClient(CIO) {
        install(Auth) {
            basic {
                //sendWithoutRequest = true
                username = "mummy"
                password = "???"
            }
        }
    }

    //

    suspend fun get(url: String): String {
        try {
            val response: HttpResponse = client.get(url)
            val stringBody: String = response.receive()
            return stringBody
        } catch (e: ClientRequestException) {
            return "ClientRequestException"
        } catch (e: ServerResponseException) {
            return "ServerResponseException"
        } catch (e: ConnectException) {
            return "ConnectException"
        }
    }

    //

    suspend fun post(url: String, body_content: String): String {
        try {
            val response: HttpResponse = client.post(url) {
                body = body_content
            }
            val stringBody: String = response.receive()
            return stringBody
        } catch (e: ServerResponseException) {
            return "ClientRequestException"
        } catch (e: ServerResponseException) {
            return "ServerResponseException"
        } catch (e: ConnectException) {
            return "ConnectException"
        }
    }
}
