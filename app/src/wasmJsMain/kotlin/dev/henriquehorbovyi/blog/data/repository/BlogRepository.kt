package dev.henriquehorbovyi.blog.data.repository

import dev.henriquehorbovyi.blog.data.BlogPostContent
import dev.henriquehorbovyi.blog.data.BlogPostPreview

class BlogRepository : IBlogRepository {

    private val fakePosts = listOf(
        BlogPostPreview(
            title = "Demo Blog Post",
            file = "demo-blog-post.md",
            publishedAt = "Jul 27, 2025"
        )
    )

    override suspend fun getPosts(): List<BlogPostPreview> {
        return fakePosts
        /*return KtorClient.client
            .get("https://raw.githubusercontent.com/henriquehorbovyi/blog/main/posts/index.json")
            .body()
*/
    }

    override suspend fun getPostByFileName(fileName: String): BlogPostContent {
        //TODO: testing with this url: https://raw.githubusercontent.com/henriquehorbovyi/my-blog/refs/heads/main/app/blog/posts/wip/static-typing.mdx
        return BlogPostContent(
            title = "Demo Blog Post",
            content = "# This is a demo blog post\n\nThis is a demo blog post content.",
            publishedAt = "Jul 27, 2025"
        )
    }
}
