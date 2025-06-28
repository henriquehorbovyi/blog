package dev.henriquehorbovyi.blog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.henriquehorbovyi.blog.data.repository.IBlogRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlogPostViewModel(
    private val repository: IBlogRepository
) : ViewModel(), IBlogPostViewModel {

    override val navigation = MutableSharedFlow<BlogPostNavigationEvent>(extraBufferCapacity = 1)
    override val uiState = MutableStateFlow<BlogPostsUiState>(BlogPostsUiState.Loading)

    init {
        viewModelScope.launch {
            val posts = repository.getPosts()
            uiState.update { BlogPostsUiState.Content(posts = posts) }
        }
    }

    override fun onAction(action: BlogPostAction) {
        when (action) {
            is BlogPostAction.BlogPostClicked -> navigateToPostDetail(action.id)
        }
    }

    private fun navigateToPostDetail(id: String) {
        viewModelScope.launch {
            navigation.emit(BlogPostNavigationEvent.OpenBlogPost(id))
        }
    }
}


