package dev.henriquehorbovyi.blog.data.repository

import dev.henriquehorbovyi.blog.data.BlogPost
import kotlinx.coroutines.delay

//TODO:
// - move data package to data module
// - create a service to fetch the posts from an api

class BlogRepository : IBlogRepository {

    private val fakePosts = listOf(
        BlogPost(
            title = "My First Blog Post",
            content = "This is the content of my first blog post. Exciting!",
            publishedAt = "Jul 28, 2024"
        ),
        BlogPost(
            title = "Compose Multiplatform Rocks",
            content = "Exploring the wonders of Compose Multiplatform. It's awesome for UI development.",
            publishedAt = "Jul 29, 2024"
        ),
        BlogPost(
            title = "Understanding StateFlow in Kotlin",
            content = "A deep dive into StateFlow and how it helps manage state in modern Kotlin applications.",
            publishedAt = "Jul 30, 2024"
        )
    )

    override suspend fun getPosts(): List<BlogPost> {
        delay(1000)
        return fakePosts
    }
}