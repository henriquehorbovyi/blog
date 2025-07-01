package dev.henriquehorbovyi.blog.viewmodel.postdetails

interface PostDetailAction {
    data class LoadPostDetail(val fileName: String) : PostDetailAction
    data class Comment(val content: String) : PostDetailAction
    object Like : PostDetailAction
}