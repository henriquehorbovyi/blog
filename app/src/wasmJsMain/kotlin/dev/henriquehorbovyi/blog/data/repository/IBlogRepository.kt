package dev.henriquehorbovyi.blog.data.repository

import dev.henriquehorbovyi.blog.data.PostContent
import dev.henriquehorbovyi.blog.data.PostPreview

interface IBlogRepository {
    suspend fun getPosts(): List<PostPreview>
    suspend fun getPostByFileName(fileName: String): PostContent
}