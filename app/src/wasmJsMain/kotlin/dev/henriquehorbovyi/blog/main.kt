package dev.henriquehorbovyi.blog

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.henriquehorbovyi.blog.data.repository.BlogRepository
import dev.henriquehorbovyi.blog.viewmodel.BlogPostViewModel
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val repository = BlogRepository()
        val viewModel = BlogPostViewModel(repository)
        App(viewModel)

    }
}
