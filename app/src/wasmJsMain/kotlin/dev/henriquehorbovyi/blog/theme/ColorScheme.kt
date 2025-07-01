package dev.henriquehorbovyi.blog.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Simple Black and White Light Theme
internal val LightAppColors = lightColorScheme(
    primary = Color(0xFF101010),
    onPrimary = Color.White,
    secondary = Color(0xFFA8A29D),
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Color(0xFF101010),
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color.Black,
    outline = Color(0xFFE0E0E0)
)

// Simple Black and White Dark Theme
internal val DarkAppColors = darkColorScheme(
    primary = Color(0xFFF1F1F1),
    onPrimary = Color.Black,
    secondary = Color(0xFF666666),
    onSecondary = Color.Black,
    background = Color(0xFF0f0f0f),
    onBackground = Color(0xFFcccccc),
    surface = Color.Black,
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black,
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = Color.White,
    outline = Color(0xFF3C3C3C),
)
