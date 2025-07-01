package dev.henriquehorbovyi.blog.viewmodel

import dev.henriquehorbovyi.blog.navigation.Page

interface PostNavigationEvent {
    data class OpenBlogPost(val blogPostFileName: String) : PostNavigationEvent
    data class NavigateToPage(val page: Page) : PostNavigationEvent
}