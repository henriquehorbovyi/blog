package dev.henriquehorbovyi.blog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.henriquehorbovyi.blog.AppCookies.IS_DARK_MODE
import kotlinx.browser.document

object AppCookies {
    const val IS_DARK_MODE = "isDarkMode"

    private val mutableMap: MutableMap<String, String> = mutableMapOf()

    init {
        loadCookies()
    }

    fun push(key: String, value: String) {
        mutableMap[key] = value
        syncCookies()
    }

    fun get(key: String): String? = mutableMap.getOrElse(key) {
        null
    }

    private fun loadCookies() {
        try {
            document.cookie.split("; ")
                .map { it.split("=") }
                .forEach { (key, value) ->
                    mutableMap[key] = value
                }
        } catch (e: Exception) {
            println("Error loading cookies: ${e.message}")
        }
    }

    private fun syncCookies() {
        val c = mutableMap
            .map { (key, value) -> "$key=$value" }
            .joinToString("; ")
        document.cookie = c
    }
}

class AppState(
    initialIsUiLoading: Boolean = true,
    initialIsDarkMode: Boolean = AppCookies.get(IS_DARK_MODE)?.toBoolean() ?: true,
    initialShouldAddSideSpace: Boolean = true
) {
    private val cookies = AppCookies
    var isUiLoading by mutableStateOf(initialIsUiLoading)
    var isDarkMode by mutableStateOf(initialIsDarkMode)
    var shouldAddSideSpace by mutableStateOf(initialShouldAddSideSpace)

    fun updateIsUiLoading(newIsUiLoading: Boolean) {
        isUiLoading = newIsUiLoading
    }

    fun toggleUiMode() {
        isDarkMode = !isDarkMode
        cookies.push(IS_DARK_MODE, isDarkMode.toString())
    }

    fun updateShouldAddSideSpace(newShouldAddSideSpace: Boolean) {
        shouldAddSideSpace = newShouldAddSideSpace
    }
}

@Composable
fun rememberAppState(): AppState = remember {
    AppState()
}
