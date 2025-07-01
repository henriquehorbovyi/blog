package dev.henriquehorbovyi.blog.viewmodel.posts

import dev.henriquehorbovyi.blog.data.PostPreview

sealed interface PostsUiState {
    object Loading : PostsUiState
    data class Content(val posts: List<PostPreview>) : PostsUiState
    data class Error(val message: String) : PostsUiState
}
