package dev.henriquehorbovyi.blog.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.henriquehorbovyi.blog.data.BlogPostPreview
import dev.henriquehorbovyi.blog.theme.BlogTheme
import dev.henriquehorbovyi.blog.viewmodel.BlogPostsUiState

@Composable
fun BlogPosts(
    modifier: Modifier = Modifier,
    blogPostsUiState: BlogPostsUiState,
    onPostClicked: (String) -> Unit
) {
    when (blogPostsUiState) {
        is BlogPostsUiState.Content -> Content(
            modifier = modifier,
            posts = blogPostsUiState.posts,
            onPostClicked = onPostClicked
        )
        is BlogPostsUiState.Error -> ErrorState(message = blogPostsUiState.message)
        BlogPostsUiState.Loading -> ProgressIndicator()
    }
}

@Composable
private fun Content(
    posts: List<BlogPostPreview>,
    modifier: Modifier = Modifier,
    onPostClicked: (String) -> Unit
) {
    if (posts.isEmpty()) {
        EmptyState(modifier = modifier)
    } else {
        LazyColumn(modifier = modifier) {
            items(posts) {
                BlogPostItem(
                    blogPostPreview = it,
                    modifier = Modifier.padding(bottom = 16.dp),
                    onPostClicked = onPostClicked
                )

            }
        }
    }
}

@Composable
private fun BlogPostItem(
    modifier: Modifier = Modifier,
    blogPostPreview: BlogPostPreview,
    onPostClicked: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPostClicked(blogPostPreview.file) },
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = blogPostPreview.publishedAt,
            style = BlogTheme.typography.bodyMedium,
            color = BlogTheme.colorScheme.secondary
        )
        Text(
            text = blogPostPreview.title,
            style = BlogTheme.typography.bodyLarge,
            color = BlogTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "No posts yet 📝",
            style = BlogTheme.typography.bodyLarge,
            color = BlogTheme.colorScheme.secondary
        )
    }
}

@Composable
fun ErrorState(modifier: Modifier = Modifier, message: String) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = BlogTheme.typography.bodyLarge,
            color = BlogTheme.colorScheme.error
        )
    }
}
