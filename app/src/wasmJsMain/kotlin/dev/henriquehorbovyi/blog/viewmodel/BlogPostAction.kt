package dev.henriquehorbovyi.blog.viewmodel

interface BlogPostAction {
    data class BlogPostClicked(val fileName: String) : BlogPostAction
}
