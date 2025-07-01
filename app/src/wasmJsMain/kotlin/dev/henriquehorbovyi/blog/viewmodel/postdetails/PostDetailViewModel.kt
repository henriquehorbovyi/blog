package dev.henriquehorbovyi.blog.viewmodel.postdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.henriquehorbovyi.blog.data.repository.BlogRepository
import dev.henriquehorbovyi.blog.data.repository.IBlogRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val repository: IBlogRepository
) : ViewModel(), IPostDetailViewModel {

    override val navigation = MutableSharedFlow<PostDetailNavigationEvent>(extraBufferCapacity = 1)
    override val uiState = MutableStateFlow<PostDetailUiState>(PostDetailUiState.Loading)

    override fun onAction(action: PostDetailAction) {
        when (action) {
            is PostDetailAction.LoadPostDetail -> handleOnLoadPostDetail(action.fileName)
            is PostDetailAction.Comment -> handleOnComment()
            PostDetailAction.Like -> handleOnLike()
        }
    }

    private fun handleOnLoadPostDetail(fileName: String) {
        viewModelScope.launch {
            uiState.update { PostDetailUiState.Loading }
            val state = try {
                val postDetail = repository.getPostByFileName(fileName)
                PostDetailUiState.Content(postDetail)
            } catch (e: Exception) {
                PostDetailUiState.Error(e.message ?: "Error")

            }
            uiState.update { state }
        }
    }

    private fun handleOnLike() {
        // TODO
    }

    private fun handleOnComment() {
        // TODO
    }
}