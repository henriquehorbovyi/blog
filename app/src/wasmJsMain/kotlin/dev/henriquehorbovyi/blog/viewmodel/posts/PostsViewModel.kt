package dev.henriquehorbovyi.blog.viewmodel.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.henriquehorbovyi.blog.data.repository.IBlogRepository
import dev.henriquehorbovyi.blog.navigation.Page
import dev.henriquehorbovyi.blog.viewmodel.PostAction
import dev.henriquehorbovyi.blog.viewmodel.PostNavigationEvent
import dev.henriquehorbovyi.blog.viewmodel.IPostsViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlogPostsViewModel(
    private val repository: IBlogRepository
) : ViewModel(), IPostsViewModel {

    override val navigation = MutableSharedFlow<PostNavigationEvent>(extraBufferCapacity = 1)
    override val uiState = MutableStateFlow<PostsUiState>(PostsUiState.Loading)

    init {
        viewModelScope.launch {
            val posts = repository.getPosts()
            uiState.update { PostsUiState.Content(posts = posts) }
        }
    }

    override fun onAction(action: PostAction) {
        when (action) {
            is PostAction.PageChanged -> navigateToPage(action.page)
            is PostAction.BlogPostClicked -> navigateToPostDetail(action.fileName)
        }
    }

    private fun navigateToPage(page: Page) {
        viewModelScope.launch {
            navigation.emit(PostNavigationEvent.NavigateToPage(page))
        }
    }

    private fun navigateToPostDetail(fileName: String) {
        viewModelScope.launch {
            navigation.emit(PostNavigationEvent.OpenBlogPost(fileName))
        }
    }
}


