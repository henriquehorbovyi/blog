package dev.henriquehorbovyi.blog.data.repository

import dev.henriquehorbovyi.blog.data.BlogPost

interface IBlogRepository {
    suspend fun getPosts(): List<BlogPost>
}