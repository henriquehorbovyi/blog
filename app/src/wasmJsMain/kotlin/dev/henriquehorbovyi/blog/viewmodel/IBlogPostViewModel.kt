package dev.henriquehorbovyi.blog.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IBlogPostViewModel {
    val navigation: Flow<BlogPostNavigationEvent>
    val uiState: StateFlow<BlogPostsUiState>

    fun onAction(action: BlogPostAction)
}
