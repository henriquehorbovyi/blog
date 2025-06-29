package dev.henriquehorbovyi.blog

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToNavigation
import dev.henriquehorbovyi.blog.data.repository.BlogRepository
import dev.henriquehorbovyi.blog.navigation.Page
import dev.henriquehorbovyi.blog.navigation.mapToUrlRoute
import dev.henriquehorbovyi.blog.viewmodel.BlogPostViewModel
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class,
    ExperimentalSerializationApi::class
)
fun main() {
    ComposeViewport(document.body!!) {
        val repository = BlogRepository()
        val viewModel = BlogPostViewModel(repository)
        App(
            blogPostViewModel = viewModel,
            onNavHostReady = { navController ->
                window.bindToNavigation(
                    navController = navController,
                    getBackStackEntryRoute = { entry -> entry.mapToUrlRoute() }
                )
            }
        )
    }
}
