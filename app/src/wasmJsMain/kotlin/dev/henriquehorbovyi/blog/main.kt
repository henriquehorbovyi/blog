package dev.henriquehorbovyi.blog

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToNavigation
import dev.henriquehorbovyi.blog.data.repository.BlogRepository
import dev.henriquehorbovyi.blog.navigation.ExternalNavigator
import dev.henriquehorbovyi.blog.navigation.Page
import dev.henriquehorbovyi.blog.navigation.mapToUrlRoute
import dev.henriquehorbovyi.blog.viewmodel.postdetails.PostDetailViewModel
import dev.henriquehorbovyi.blog.viewmodel.posts.BlogPostsViewModel
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
        val externalNavigator = ExternalNavigator(window)
        val postsViewModel = BlogPostsViewModel(repository, externalNavigator)
        val postDetailsViewModel = PostDetailViewModel(repository)
        App(
            startDestination = Page.urlToPage(document.URL),
            onNavHostReady = { navController ->
                window.bindToNavigation(
                    navController = navController,
                    getBackStackEntryRoute = { it.mapToUrlRoute() }
                )
            },
            postsViewModel = postsViewModel,
            postDetailViewModel = postDetailsViewModel
        )
    }
}
