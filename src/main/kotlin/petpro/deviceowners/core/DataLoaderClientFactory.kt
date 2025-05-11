package petpro.deviceowners.core

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object DataLoaderClientFactory {
    fun build(serviceUrl: String): HttpClient {
        return HttpClient(CIO){
            defaultRequest {
                url(serviceUrl)
            }
            install(ContentNegotiation) {
                json(Json{
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}
