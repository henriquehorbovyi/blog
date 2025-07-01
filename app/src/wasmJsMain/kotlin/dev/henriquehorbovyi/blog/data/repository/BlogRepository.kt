package dev.henriquehorbovyi.blog.data.repository

import dev.henriquehorbovyi.blog.data.PostContent
import dev.henriquehorbovyi.blog.data.PostPreview
import dev.henriquehorbovyi.blog.data.remote.BlogApiClient

class BlogRepository(
    private val client: BlogApiClient = BlogApiClient()
) : IBlogRepository {

    override suspend fun getPosts(): List<PostPreview> =
        client.getPosts().posts

    override suspend fun getPostByFileName(fileName: String): PostContent =
        client.getPost(fileName)
}
