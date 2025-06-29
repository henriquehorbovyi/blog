package dev.henriquehorbovyi.blog.data

data class BlogPostPreview(
    val id: Int = 0,
    val title: String,
    val publishedAt: String,
    val file: String
)

data class BlogPostContent(
    val id: Int = 0,
    val title: String,
    val content: String,
    val publishedAt: String
)