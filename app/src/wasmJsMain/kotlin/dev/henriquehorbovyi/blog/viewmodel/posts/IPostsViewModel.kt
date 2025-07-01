package dev.henriquehorbovyi.blog.viewmodel.posts

import dev.henriquehorbovyi.blog.viewmodel.PostNavigationEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IPostsViewModel {
    val navigation: Flow<PostNavigationEvent>
    val uiState: StateFlow<PostsUiState>

    fun onAction(action: PostAction)
}
