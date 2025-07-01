package dev.henriquehorbovyi.blog.viewmodel.postdetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IPostDetailViewModel {
    val navigation: Flow<PostDetailNavigationEvent>
    val uiState: StateFlow<PostDetailUiState>
    
    fun onAction(action: PostDetailAction)
}
