package dev.henriquehorbovyi.blog.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composeresources.generated.resources.Res
import composeresources.generated.resources.github_link
import composeresources.generated.resources.github_link_title
import dev.henriquehorbovyi.blog.theme.BlogTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    onNavigateToExternalLink: (String) -> Unit = {}
) {
    val githubLinkTitle = stringResource(Res.string.github_link_title)
    val githubLink = stringResource(Res.string.github_link)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = githubLinkTitle,
                fontSize = 16.sp,
                color = BlogTheme.colorScheme.secondary,
                modifier = Modifier
                    .clickable { onNavigateToExternalLink(githubLink) }
                    .pointerHoverIcon(PointerIcon.Hand, true)
            )
            // TODO: more links
        }

        Text(
            "© 2025 MIT Licensed",
            fontSize = 14.sp,
            color = BlogTheme.colorScheme.secondary
        )
    }
}
