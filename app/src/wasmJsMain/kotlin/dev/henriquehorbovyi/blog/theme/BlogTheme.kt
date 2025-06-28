package dev.henriquehorbovyi.blog.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object BlogTheme {
    /**
     * Retrieves the current [ColorScheme] for the BlogTheme.
     * This should be used within a composable that is a descendant of the `BlogTheme` composable.
     */
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    /**
     * Retrieves the current [Typography] for the BlogTheme.
     * This should be used within a composable that is a descendant of the `BlogTheme` composable.
     */
    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography
}

@Composable
fun BlogTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) {
        DarkAppColors
    } else {
        LightAppColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = BlogAppTypography,
        content = content
    )
}
