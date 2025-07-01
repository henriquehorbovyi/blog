package dev.henriquehorbovyi.blog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.DummyPointerIcon
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.PointerInputFilter
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import composeresources.generated.resources.Res
import composeresources.generated.resources.ic_moon
import composeresources.generated.resources.ic_sun
import dev.henriquehorbovyi.blog.navigation.Page
import dev.henriquehorbovyi.blog.theme.BlogTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun Header(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    onPageChange: (Page) -> Unit
) {
    val switchThemeIconRes = if (isDarkMode) Res.drawable.ic_sun else Res.drawable.ic_moon

    var isHomeLinkHovered by remember { mutableStateOf(false) }
    var isAboutLinkHovered by remember { mutableStateOf(false) }
    var isSwitchThemeHovered by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {

            HoverBox(
                onHovered = { isHomeLinkHovered = it },
                content = {
                    Text(
                        text = "home",
                        style = BlogTheme.typography.bodyLarge,
                        color = BlogTheme.colorScheme.onBackground.copy(
                            alpha = if (isHomeLinkHovered) 0.66f else 1f
                        ),
                        modifier = Modifier
                            .clickable(
                                onClick = { onPageChange(Page.Home) },
                                indication = null,
                                interactionSource = null,
                            ),
                    )

                }
            )

            HoverBox(
                onHovered = { isAboutLinkHovered = it },
                content = {
                    Text(
                        text = "blog",
                        style = BlogTheme.typography.bodyLarge,
                        color = BlogTheme.colorScheme.onBackground.copy(
                            alpha = if (isAboutLinkHovered) 0.66f else 1f
                        ),

                        modifier = Modifier
                            .clickable(
                                onClick = { onPageChange(Page.Blog) },
                                indication = null,
                                interactionSource = null,
                            )
                    )
                }
            )

        }

        HoverBox(
            onHovered = { isSwitchThemeHovered = it },
            content = {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp)
                        .clickable { onThemeToggle() }
                        .background(if (isSwitchThemeHovered) BlogTheme.colorScheme.surfaceVariant else Color.Transparent)
                        .padding(4.dp),
                    painter = painterResource(switchThemeIconRes),
                    colorFilter = ColorFilter.tint(BlogTheme.colorScheme.onSurfaceVariant),
                    contentDescription = null
                )
            }
        )
    }
}


@Composable
fun HoverBox(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onHovered: (Boolean) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    LaunchedEffect(isHovered, onHovered) {
        onHovered(isHovered)
    }

    Box(modifier = modifier.hoverable(interactionSource).pointerHoverIcon(PointerIcon.Hand, true)) {
        content()
    }
}