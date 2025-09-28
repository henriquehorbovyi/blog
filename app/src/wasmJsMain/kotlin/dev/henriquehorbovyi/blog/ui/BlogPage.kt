package dev.henriquehorbovyi.blog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.henriquehorbovyi.blog.theme.BlogTheme

@Composable
fun BlogPage(
    blogPostsContent: @Composable () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "My Blog",
            style = BlogTheme.typography.headlineLarge,
            color = BlogTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(32.dp))


        blogPostsContent()
    }
}