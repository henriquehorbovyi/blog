package dev.henriquehorbovyi.blog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.henriquehorbovyi.blog.navigation.Page

class AppState(
    initialCurrentPage: Page,
    initialIsUiLoading: Boolean,
    initialIsDarkMode: Boolean,
    initialShouldAddSideSpace: Boolean
) {
    var currentPage by mutableStateOf(initialCurrentPage)
    var isUiLoading by mutableStateOf(initialIsUiLoading)
    var isDarkMode by mutableStateOf(initialIsDarkMode)
    var shouldAddSideSpace by mutableStateOf(initialShouldAddSideSpace)

    fun updateCurrentPage(newPage: Page) {
        currentPage = newPage
    }

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
    initialCurrentPage: Page = Page.HOME,
    initialIsUiLoading: Boolean = true,
    initialIsDarkMode: Boolean = true,
    initialShouldAddSideSpace: Boolean = true,
): AppState {

    return remember {
        AppState(
            initialCurrentPage = initialCurrentPage,
            initialIsUiLoading = initialIsUiLoading,
            initialIsDarkMode = initialIsDarkMode,
            initialShouldAddSideSpace = initialShouldAddSideSpace
        )
    }
}
