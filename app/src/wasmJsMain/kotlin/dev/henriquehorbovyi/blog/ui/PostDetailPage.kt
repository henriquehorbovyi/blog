package dev.henriquehorbovyi.blog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.henriquehorbovyi.blog.components.ProgressIndicator
import dev.henriquehorbovyi.blog.theme.BlogMarkdown
import dev.henriquehorbovyi.blog.theme.BlogMarkdownColorStyle
import dev.henriquehorbovyi.blog.viewmodel.postdetails.PostDetailUiState

@Composable
fun PostDetailPage(
    fileName: String,
    uiState: PostDetailUiState,
    loadPostContent: (String) -> Unit,
    handleOpenExternalLink: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        loadPostContent(fileName)
    }

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is PostDetailUiState.Loading -> ProgressIndicator()
            is PostDetailUiState.Content -> Content(
                uiState = uiState,
                handleOpenExternalLink = handleOpenExternalLink
            )
        }
    }
}

@Composable
private fun Content(uiState: PostDetailUiState.Content, handleOpenExternalLink: () -> Unit) {
    val content = uiState.model.content.trimIndent()

    BlogMarkdown(
        content = content,
        colorStyle = BlogMarkdownColorStyle.Default
    )
}
