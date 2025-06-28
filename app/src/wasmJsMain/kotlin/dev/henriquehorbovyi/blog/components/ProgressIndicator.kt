package dev.henriquehorbovyi.blog.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.henriquehorbovyi.blog.theme.BlogTheme

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(
            modifier = modifier.padding(8.dp),
            color = BlogTheme.colorScheme.primary,
            strokeWidth = 2.dp,
            trackColor = BlogTheme.colorScheme.surfaceVariant,
        )
    }
}