package dev.henriquehorbovyi.blog.data.repository

import dev.henriquehorbovyi.blog.data.BlogPostContent
import dev.henriquehorbovyi.blog.data.BlogPostPreview

interface IBlogRepository {
    suspend fun getPosts(): List<BlogPostPreview>
    suspend fun getPostByFileName(fileName: String): BlogPostContent
}