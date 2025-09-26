package dev.henriquehorbovyi.blog.data.remote

import dev.henriquehorbovyi.blog.data.PostContent
import dev.henriquehorbovyi.blog.data.PostsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BlogApiClient {

    private val postsEndpoint =
        "https://raw.githubusercontent.com/henriquehorbovyi/blog/refs/heads/main/posts/"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
            register(
                ContentType.Any, KotlinxSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            )
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }

    suspend fun getPosts(): PostsResponse {
        return httpClient
            .get(postsEndpoint.plus("index.json"))
            .body() ?: PostsResponse(emptyList())
    }

    suspend fun getPost(fileName: String): PostContent {
        val response: HttpResponse = httpClient.get(postsEndpoint.plus(fileName))
        val content = response.bodyAsText()
        // TODO, think about a way to add id, title, publishedAt or PostContent carries only the content itself
        return PostContent( content = content)
    }

}
