package dev.henriquehorbovyi.blog.viewmodel

interface BlogPostNavigationEvent {
    data class OpenBlogPost(val blogPostFileName: String) : BlogPostNavigationEvent
}