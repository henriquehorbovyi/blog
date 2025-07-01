package dev.henriquehorbovyi.blog.viewmodel.posts

import dev.henriquehorbovyi.blog.navigation.Page

interface PostAction {
    data class BlogPostClicked(val fileName: String) : PostAction
    data class PageChanged(val page: Page) : PostAction
    data class OpenExternalLink(val url: String) : PostAction
}
