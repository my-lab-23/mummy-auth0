package com.example.my_http

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*

object MyHTTP {

    val client = HttpClient() {
        install(Auth) {
            basic {
                sendWithoutRequest = true
                username = "mummy"
                password = "???"
            }
        }
    }

    val client_id = "???"
    val client_secret = "???"
    val refresh_token = "???"

    suspend fun take_token(): String {
        val response: HttpResponse = client.post("???.auth0.com/oauth/token") {
            headers {
                append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
            }
            body = "grant_type=refresh_token&" +
                   "client_id=$client_id&" +
                   "client_secret=$client_secret&" +
                   "refresh_token=$refresh_token"
        }
        val stringBody: String = response.receive()
        return stringBody
    }

    suspend fun send_token(access_token: String): String {
        val response: HttpResponse = client.get("???") {
            headers {
                append("access_token", access_token)
            }
        }
        val stringBody: String = response.receive()
        return stringBody
    }
}
