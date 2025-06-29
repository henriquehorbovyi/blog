package dev.henriquehorbovyi.blog.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

sealed class Page {
    @Serializable
    object Home : Page()

    @Serializable
    object Blog : Page()

    @Serializable
    data class BlogPost(var file: String) : Page()


}

@OptIn(ExperimentalSerializationApi::class)
fun NavBackStackEntry.mapToUrlRoute(): String {
    val route = destination.route.orEmpty()
    return when {
        route.startsWith(Page.Home.serializer().descriptor.serialName) -> "#home"
        route.startsWith(Page.Blog.serializer().descriptor.serialName) -> "#blog"
        route.startsWith(Page.BlogPost.serializer().descriptor.serialName) -> {
            val args = toRoute<Page.BlogPost>()
            "#blog_post_${args.file}"
        }
        // Doesn't set a URL fragment for all other routes
        else -> ""
    }
}