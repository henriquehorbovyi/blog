package dev.henriquehorbovyi.blog.viewmodel.posts

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface IPostsViewModel {
    val navigation: Channel<PostNavigationEvent>
    val uiState: StateFlow<PostsUiState>

    fun onAction(action: PostAction)
}
