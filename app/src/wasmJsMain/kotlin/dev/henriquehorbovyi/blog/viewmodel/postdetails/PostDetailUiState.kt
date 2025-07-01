package dev.henriquehorbovyi.blog.viewmodel.postdetails

import dev.henriquehorbovyi.blog.data.PostContent
import dev.henriquehorbovyi.blog.navigation.Page

interface PostDetailUiState {
    object Loading : PostDetailUiState
    data class Content(val model: PostContent) : PostDetailUiState
    data class Error(val message: String) : PostDetailUiState
}