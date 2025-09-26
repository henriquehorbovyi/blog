package dev.henriquehorbovyi.blog.viewmodel.postdetails

import dev.henriquehorbovyi.blog.data.PostContent

interface PostDetailUiState {
    object Loading : PostDetailUiState
    data class Content(val model: PostContent) : PostDetailUiState
    data class Error(val message: String) : PostDetailUiState
}