package dev.henriquehorbovyi.blog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.henriquehorbovyi.blog.theme.BlogTheme

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    blogPostsContent: @Composable () -> Unit
) {
    val message = remember {
        """
            I'm Henrique, and welcome to my corner of the Internet, where I share insights, experiments, and discoveries from the world of code.
            I spend my days (and nights) crafting mobile applications 📱, diving into game development 👾, and building sleek UI components that probably only I will ever appreciate.
            This blog is where I document the journey – the breakthroughs, the late-night debugging sessions, and everything in between.
        """.trimIndent()
    }

    Column(
        modifier = modifier
    ) {
        Text(
            "Hello \uD83D\uDC4B",
            style = BlogTheme.typography.headlineLarge,
            color = BlogTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(32.dp))


        Text(
            text = message,
            style = BlogTheme.typography.bodyLarge,
            color = BlogTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(64.dp))

        blogPostsContent()
    }
}