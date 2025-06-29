package dev.henriquehorbovyi.blog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import composeresources.generated.resources.Res
import composeresources.generated.resources.noto_color_emoji
import composeresources.generated.resources.roboto
import dev.henriquehorbovyi.blog.components.BlogPosts
import dev.henriquehorbovyi.blog.components.Footer
import dev.henriquehorbovyi.blog.components.Header
import dev.henriquehorbovyi.blog.components.ProgressIndicator
import dev.henriquehorbovyi.blog.navigation.Page.Blog
import dev.henriquehorbovyi.blog.navigation.Page.BlogPost
import dev.henriquehorbovyi.blog.navigation.Page.Home
import dev.henriquehorbovyi.blog.theme.BlogTheme
import dev.henriquehorbovyi.blog.ui.BlogPageContent
import dev.henriquehorbovyi.blog.ui.BlogPostDetail
import dev.henriquehorbovyi.blog.ui.HomePageContent
import dev.henriquehorbovyi.blog.viewmodel.BlogPostViewModel
import dev.henriquehorbovyi.blog.viewmodel.BlogPostsUiState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    blogPostViewModel: BlogPostViewModel,
    onNavHostReady: suspend (NavController) -> Unit = {}

) {
    val appState = rememberAppState()
    val uiState by blogPostViewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    onWindowSizeChanged(
        onCompact = { appState.updateShouldAddSideSpace(false) },
        onMedium = { appState.updateShouldAddSideSpace(false) },
        onExpanded = { appState.updateShouldAddSideSpace(true) }
    )

    // TODO: Move to a init font block
    val fonts = listOf(
        Font(Res.font.noto_color_emoji),
        Font(Res.font.roboto),
    )
    val appFontFamily = FontFamily(fonts)
    val fontFamilyResolver = LocalFontFamilyResolver.current
    var loadedFonts by remember { mutableStateOf(0) }

    LaunchedEffect(appFontFamily) {
        try {
            fontFamilyResolver.preload(appFontFamily)
            loadedFonts++

            if (loadedFonts == fonts.size + 1) {
                println("Emoji font preloaded successfully!")
                appState.updateIsUiLoading(false)
            }
        } catch (e: Exception) {
            println("Error preloading emoji font: ${e.message}")
            appState.updateIsUiLoading(false) // handle error case
        }
    }

    BlogTheme(useDarkTheme = appState.isDarkMode) {
        Box(
            Modifier
                .fillMaxSize()
                .background(BlogTheme.colorScheme.background)
                .padding(24.dp),
        ) {
            if (appState.isUiLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProgressIndicator()
                }
            } else {
                Content(
                    appState = appState,
                    uiState = uiState,
                    navController = navController,
                    onNavHostReady = onNavHostReady
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalFoundationApi::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    appState: AppState,
    uiState: BlogPostsUiState,
    navController: NavHostController,
    onNavHostReady: suspend (NavController) -> Unit
) {

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        if (appState.shouldAddSideSpace) {
            Spacer(Modifier.weight(0.25f))
        }

        Column(modifier = Modifier.weight(if (appState.shouldAddSideSpace) 0.5f else 1f)) {
            Header(
                isDarkMode = appState.isDarkMode,
                onThemeToggle = { appState.toggleUiMode() },
                onPageChange = { navController.navigate(it) }
            )
            Spacer(Modifier.height(64.dp))
            SelectionContainer {
                NavHost(startDestination = Home, navController = navController) {
                    composable<Home> {
                        HomePageContent(blogPostsContent = {
                            BlogPosts(
                                blogPostsUiState = uiState,
                                onPostClicked = { file ->
                                    navController.navigate(BlogPost(file))
                                }
                            )
                        })
                    }
                    composable<Blog> {
                        BlogPageContent(blogPostsContent = {
                            BlogPosts(
                                blogPostsUiState = uiState,
                                onPostClicked = { file ->
                                    navController.navigate(BlogPost(file))
                                }
                            )
                        })
                    }

                    composable<BlogPost> { backStackEntry ->
                        val file = backStackEntry.toRoute<BlogPost>().file
                        BlogPostDetail(file = file)
                    }
                }
            }

            Spacer(Modifier.height(100.dp))
            Footer()
        }
        if (appState.shouldAddSideSpace) {
            Spacer(Modifier.weight(0.25f))
        }
    }

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }
}
