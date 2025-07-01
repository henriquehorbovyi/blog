package dev.henriquehorbovyi.blog.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import composeresources.generated.resources.Res
import composeresources.generated.resources.jetbrains_mono
import composeresources.generated.resources.noto_color_emoji
import composeresources.generated.resources.roboto
import org.jetbrains.compose.resources.Font

val BlogAppFontFamily: FontFamily
    @Composable get() = FontFamily(
        listOf(
            Font(Res.font.jetbrains_mono),
            Font(Res.font.noto_color_emoji),
        )
    )

// Custom Typography Definition using BlogAppFontFamily
val BlogAppTypography: Typography
    @Composable get() = Typography(
        // Markdown -> #
        displayLarge = TextStyle(
            fontFamily = BlogAppFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 42.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp,
            color = BlogTheme.colorScheme.onPrimary
        ),
        headlineLarge = TextStyle(
            fontFamily = BlogAppFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = BlogAppFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = BlogAppFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = BlogAppFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 32.sp,
            letterSpacing = 1.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = BlogAppFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = BlogAppFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )

