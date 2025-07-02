package dev.henriquehorbovyi.blog.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.compose.MarkdownElement
import com.mikepenz.markdown.compose.components.CurrentComponentsBridge.heading3
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownDivider
import com.mikepenz.markdown.compose.elements.MarkdownHeader
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.compose.elements.MarkdownParagraph
import com.mikepenz.markdown.compose.elements.MarkdownText
import com.mikepenz.markdown.compose.elements.material.MarkdownBasicText
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import dev.henriquehorbovyi.blog.components.ProgressIndicator
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxThemes

// Extension functions for BlogTheme to provide markdown styling
object BlogMarkdownTheme {

    /**
     * Creates markdown typography that adapts to your BlogTheme typography
     */
    @Composable
    fun typography() = markdownTypography(
        // h1 == #
        h1 = BlogTheme.typography.displayLarge.copy(
            lineHeight = BlogTheme.typography.displayLarge.fontSize * 1.25,
            letterSpacing = (-0.5).sp
        ),
        // h3 == ###
        h3 = BlogTheme.typography.displaySmall.copy(
            lineHeight = BlogTheme.typography.displaySmall.fontSize * 1.33,
            fontWeight = FontWeight.SemiBold
        ),
        // h6 == ######
        h6 = BlogTheme.typography.bodyMedium.copy(
            lineHeight = BlogTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Medium,
            color = BlogTheme.colorScheme.secondary
        ),
        text = BlogTheme.typography.bodyLarge.copy(
            lineHeight = BlogTheme.typography.bodyLarge.fontSize * 1.5
        ),
        code = BlogTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily.Monospace,
            lineHeight = BlogTheme.typography.bodyMedium.fontSize * 1.43,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFcccccc)
        ),
        inlineCode = BlogTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium,
            lineHeight = BlogTheme.typography.bodyMedium.fontSize * 1.43
        ),
        quote = BlogTheme.typography.bodyLarge.copy(
            lineHeight = BlogTheme.typography.bodyLarge.fontSize * 1.5,
            color = BlogTheme.colorScheme.secondary
        ),
        paragraph = BlogTheme.typography.bodyLarge.copy(
            lineHeight = BlogTheme.typography.bodyLarge.fontSize * 1.5,
        ),
        ordered = BlogTheme.typography.bodyLarge.copy(
            lineHeight = BlogTheme.typography.bodyLarge.fontSize * 1.5
        ),
        bullet = BlogTheme.typography.bodyLarge.copy(
            lineHeight = BlogTheme.typography.bodyLarge.fontSize * 1.5
        ),
        list = BlogTheme.typography.bodyLarge.copy(
            lineHeight = BlogTheme.typography.bodyLarge.fontSize * 1.5
        )
    )

    /**
     * Creates markdown colors that adapt to your BlogTheme color scheme
     * Automatically works with both light and dark themes
     */
    @Composable
    fun colors() = markdownColor(
        text = BlogTheme.colorScheme.onSurface.copy(alpha = 0.9f),
        codeBackground = Color(0xff1a1a1e),
        inlineCodeBackground = BlogTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        dividerColor = BlogTheme.colorScheme.outline,
    )

    /**
     * Alternative color scheme using tertiary colors for a different accent
     */
    @Composable
    fun colorsAccent() = markdownColor(
        text = BlogTheme.colorScheme.onSurface,
        codeBackground = BlogTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
        inlineCodeBackground = BlogTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f),
        dividerColor = BlogTheme.colorScheme.outline,
    )

    /**
     * Color scheme with more contrast for code elements
     */
    @Composable
    fun colorsHighContrast() = markdownColor(
        text = BlogTheme.colorScheme.onSurface,
        codeBackground = BlogTheme.colorScheme.primaryContainer,
        inlineCodeBackground = BlogTheme.colorScheme.secondaryContainer,
        dividerColor = BlogTheme.colorScheme.outline,
    )
}

@Composable
fun BlogMarkdown(
    content: String,
    modifier: Modifier = Modifier,
    colorStyle: BlogMarkdownColorStyle = BlogMarkdownColorStyle.Default
) {
    val isDarkTheme = isSystemInDarkTheme()

    val colors = when (colorStyle) {
        BlogMarkdownColorStyle.Default -> BlogMarkdownTheme.colors()
        BlogMarkdownColorStyle.Accent -> BlogMarkdownTheme.colorsAccent()
        BlogMarkdownColorStyle.HighContrast -> BlogMarkdownTheme.colorsHighContrast()
    }
    val highlightsBuilder = remember(isDarkTheme) {
        Highlights.Builder()
            .theme(SyntaxThemes.atom(darkMode = true))
            .language(SyntaxLanguage.KOTLIN)
    }

    Markdown(
        content = content,
        modifier = modifier,
        colors = colors,
        typography = BlogMarkdownTheme.typography(),
        components = markdownComponents(
            paragraph = {
                MarkdownParagraph(
                    content = it.content,
                    node = it.node,
                    modifier = Modifier.padding(bottom = 10.dp),
                )
            },
            codeBlock = {
                MarkdownHighlightedCodeBlock(
                    content = it.content,
                    node = it.node,
                    highlights = highlightsBuilder
                )
            },
            codeFence = {
                MarkdownHighlightedCodeFence(
                    content = it.content,
                    node = it.node,
                    highlights = highlightsBuilder
                )
            },
        ),

        loading = {
            ProgressIndicator()
        }
    )
}

// Enum for different color styles
enum class BlogMarkdownColorStyle {
    Default,
    Accent,
    HighContrast
}
