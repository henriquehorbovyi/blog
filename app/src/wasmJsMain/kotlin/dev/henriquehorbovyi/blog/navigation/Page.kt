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

    companion object {
        fun urlToPage(url: String): Page {
            val route = url.ifEmpty { Home.urlRoute }.substringAfter("#")
            val blogPostRegex = Regex("^blog/.+")
            val page =  when {
                route == "home" -> Home
                route == "blog" -> Blog
                blogPostRegex.matches(route) -> { // Use the regex
                    val file = route.substringAfter("blog/")
                    PostDetail(file)
                }

                else -> Home
            }
            return page
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

        else -> Page.Home.urlRoute
    }
}