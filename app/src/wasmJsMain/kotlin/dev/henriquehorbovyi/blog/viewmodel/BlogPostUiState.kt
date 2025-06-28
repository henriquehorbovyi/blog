package dev.henriquehorbovyi.blog.viewmodel

import dev.henriquehorbovyi.blog.data.BlogPost

sealed interface BlogPostsUiState {
    object Loading : BlogPostsUiState
    data class Content(val posts: List<BlogPost>) : BlogPostsUiState
    data class Error(val message: String) : BlogPostsUiState
}
