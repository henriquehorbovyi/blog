package dev.henriquehorbovyi.blog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class AppState(
    initialIsUiLoading: Boolean,
    initialIsDarkMode: Boolean,
    initialShouldAddSideSpace: Boolean
) {
    var isUiLoading by mutableStateOf(initialIsUiLoading)
    var isDarkMode by mutableStateOf(initialIsDarkMode)
    var shouldAddSideSpace by mutableStateOf(initialShouldAddSideSpace)

    fun updateIsUiLoading(newIsUiLoading: Boolean) {
        isUiLoading = newIsUiLoading
    }

    fun toggleUiMode() {
        isDarkMode = !isDarkMode
    }

    fun updateShouldAddSideSpace(newShouldAddSideSpace: Boolean) {
        shouldAddSideSpace = newShouldAddSideSpace
    }
}

@Composable
fun rememberAppState(
    initialIsUiLoading: Boolean = true,
    initialIsDarkMode: Boolean = true,
    initialShouldAddSideSpace: Boolean = true,
): AppState = remember {
    AppState(
        initialIsUiLoading = initialIsUiLoading,
        initialIsDarkMode = initialIsDarkMode,
        initialShouldAddSideSpace = initialShouldAddSideSpace
    )
}
