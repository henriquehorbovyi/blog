package dev.henriquehorbovyi.blog.viewmodel.postdetails

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface IPostDetailViewModel {
    val navigation: Channel<PostDetailNavigationEvent>
    val uiState: StateFlow<PostDetailUiState>
    
    fun onAction(action: PostDetailAction)
}
