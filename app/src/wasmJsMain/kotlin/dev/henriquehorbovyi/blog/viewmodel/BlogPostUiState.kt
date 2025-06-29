package dev.henriquehorbovyi.blog.viewmodel

import dev.henriquehorbovyi.blog.data.BlogPostPreview

sealed interface BlogPostsUiState {
    object Loading : BlogPostsUiState
    data class Content(val posts: List<BlogPostPreview>) : BlogPostsUiState
    data class Error(val message: String) : BlogPostsUiState
}
