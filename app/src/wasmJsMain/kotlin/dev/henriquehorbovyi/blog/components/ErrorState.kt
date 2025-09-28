package dev.henriquehorbovyi.blog.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.henriquehorbovyi.blog.theme.BlogTheme

@Composable
fun ErrorState(modifier: Modifier = Modifier, message: String) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = BlogTheme.typography.displaySmall,
            color = BlogTheme.colorScheme.onSurface
        )
    }
}
