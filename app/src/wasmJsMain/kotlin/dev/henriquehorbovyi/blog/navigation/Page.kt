package dev.henriquehorbovyi.blog.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
sealed class Page(val urlRoute: String) {
    @Serializable
    object Home : Page("#home")

    @Serializable
    object Blog : Page("#blog")

    @Serializable
    data class PostDetail(var file: String) : Page("#blog/$file")

    @Serializable
    object NotFound : Page("#404")

    companion object {
        fun urlToPage(url: String): Page {
            // if there's no # in the url the original url string is returned.
            val clearedRoute = url.substringAfter("#")
            if (clearedRoute.isEmpty() || clearedRoute == url) return Home

            // route = #home, #blog etc...
            val route = "#$clearedRoute"
            val blogPostRegex = Regex("^#blog/.+")

            return when {
                route == Home.urlRoute -> Home
                route == Blog.urlRoute -> Blog
                blogPostRegex.matches(route) -> {
                    val file = route.substringAfter("blog/")
                    PostDetail(file)
                }
                else -> NotFound
            }
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun NavBackStackEntry.mapToUrlRoute(): String {
    val route = destination.route.orEmpty()
    return when {
        route.startsWith(Page.Home.serializer().descriptor.serialName) -> Page.Home.urlRoute
        route.startsWith(Page.Blog.serializer().descriptor.serialName) -> Page.Blog.urlRoute
        route.startsWith(Page.PostDetail.serializer().descriptor.serialName) -> {
            Page.PostDetail(toRoute<Page.PostDetail>().file).urlRoute
        }
        route.startsWith(Page.NotFound.serializer().descriptor.serialName) -> Page.NotFound.urlRoute

        else -> Page.NotFound.urlRoute
    }
}