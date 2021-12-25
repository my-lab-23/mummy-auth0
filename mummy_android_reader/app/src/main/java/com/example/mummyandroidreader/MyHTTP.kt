package com.example.mummyandroidreader

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
                //sendWithoutRequest = true
                username = "mummy"
                password = "???"
            }
        }
    }

    suspend fun send_token(access_token: String): String {
        // android:usesCleartextTraffic="true"
        val response: HttpResponse = client.get("???/android") {
            headers {
                append("access_token", access_token)
            }
        }
        val stringBody: String = response.receive()
        return stringBody
    }
}
