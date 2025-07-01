package dev.henriquehorbovyi.blog.data

import kotlinx.serialization.Serializable

@Serializable
data class PostsResponse(
    val posts: List<PostPreview>
)

@Serializable
data class PostPreview(
    val id: Int = 0,
    val title: String,
    val publishedAt: String,
    val file: String
)

@Serializable
data class PostContent(
    val id: Int = 0,
    val title: String,
    val content: String,
    val publishedAt: String
)