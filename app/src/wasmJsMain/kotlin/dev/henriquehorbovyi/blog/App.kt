package dev.henriquehorbovyi.blog

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
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
import composeresources.generated.resources.jetbrains_mono
import composeresources.generated.resources.noto_color_emoji
import dev.henriquehorbovyi.blog.components.BlogPosts
import dev.henriquehorbovyi.blog.components.Footer
import dev.henriquehorbovyi.blog.components.Header
import dev.henriquehorbovyi.blog.components.ProgressIndicator
import dev.henriquehorbovyi.blog.navigation.Page
import dev.henriquehorbovyi.blog.navigation.Page.Blog
import dev.henriquehorbovyi.blog.navigation.Page.Home
import dev.henriquehorbovyi.blog.navigation.Page.PostDetail
import dev.henriquehorbovyi.blog.theme.BlogTheme
import dev.henriquehorbovyi.blog.ui.BlogPageContent
import dev.henriquehorbovyi.blog.ui.HomePageContent
import dev.henriquehorbovyi.blog.ui.PostDetailPage
import dev.henriquehorbovyi.blog.viewmodel.IPostsViewModel
import dev.henriquehorbovyi.blog.viewmodel.PostAction
import dev.henriquehorbovyi.blog.viewmodel.PostNavigationEvent
import dev.henriquehorbovyi.blog.viewmodel.postdetails.IPostDetailViewModel
import dev.henriquehorbovyi.blog.viewmodel.postdetails.PostDetailAction
import dev.henriquehorbovyi.blog.viewmodel.postdetails.PostDetailUiState
import dev.henriquehorbovyi.blog.viewmodel.posts.PostsUiState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    postsViewModel: IPostsViewModel,
    postDetailViewModel: IPostDetailViewModel,
    onNavHostReady: suspend (NavController) -> Unit,
    startDestination: Page = Home
) {
    val appState = rememberAppState()
    val uiState by postsViewModel.uiState.collectAsStateWithLifecycle()
    val postDetailUiState by postDetailViewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    postsViewModel.navigation.observeWithLifecycle { event ->
        when (event) {
            is PostNavigationEvent.NavigateToPage -> navController.navigate(event.page)
            is PostNavigationEvent.OpenBlogPost -> navController.navigate(PostDetail(event.blogPostFileName))
        }
    }


    onWindowSizeChanged(
        onCompact = { appState.updateShouldAddSideSpace(false) },
        onMedium = { appState.updateShouldAddSideSpace(false) },
        onExpanded = { appState.updateShouldAddSideSpace(true) }
    )

    // TODO: Move to a init font block
    val fonts = listOf(
        Font(Res.font.noto_color_emoji),
        Font(Res.font.jetbrains_mono),
    )
    val appFontFamily = FontFamily(fonts)
    val fontFamilyResolver = LocalFontFamilyResolver.current
    var loadedFonts by remember { mutableStateOf(0) }

    LaunchedEffect(appFontFamily) {
        try {
            fontFamilyResolver.preload(appFontFamily)
            loadedFonts++

            if (loadedFonts == fonts.size + 1) {
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
                    onEvent = postsViewModel::onAction,
                    navController = navController,
                    onNavHostReady = onNavHostReady,
                    postDetailUiState = postDetailUiState,
                    onPostDetailEvent = postDetailViewModel::onAction,
                    startDestination = startDestination,
                )
            }
        }
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    startDestination: Page,
    appState: AppState,
    uiState: PostsUiState,
    postDetailUiState: PostDetailUiState,
    navController: NavHostController,
    onEvent: (PostAction) -> Unit,
    onPostDetailEvent: (PostDetailAction) -> Unit,
    onNavHostReady: suspend (NavController) -> Unit,
) {

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Row {
                val weightModifier =
                    if (appState.shouldAddSideSpace) Modifier.weight(0.5f) else Modifier
                if (appState.shouldAddSideSpace) {
                    Spacer(Modifier.weight(0.25f))
                }
                Column(modifier = weightModifier) {
                    Header(
                        modifier = Modifier.padding(top = 32.dp, bottom = 64.dp),
                        isDarkMode = appState.isDarkMode,
                        onThemeToggle = { appState.toggleUiMode() },
                        onPageChange = { onEvent(PostAction.PageChanged(it)) }
                    )

                    Column(
                        modifier = Modifier
                    ) {
                        SelectionContainer {
                            NavHost(
                                startDestination = startDestination,
                                navController = navController,
                                enterTransition = { fadeIn(animationSpec = tween(180)) },
                                exitTransition = { fadeOut(animationSpec = tween(0)) },
                            ) {
                                composable<Home> {
                                    HomePageContent(
                                        modifier = Modifier.fillMaxWidth(),
                                        blogPostsContent = {
                                            BlogPosts(
                                                postsUiState = uiState,
                                                onPostClicked = { file ->
                                                    onEvent(PostAction.BlogPostClicked(file))
                                                }
                                            )
                                        }
                                    )
                                }
                                composable<Blog> {
                                    BlogPageContent(blogPostsContent = {
                                        BlogPosts(
                                            postsUiState = uiState,
                                            onPostClicked = { file ->
                                                onEvent(PostAction.BlogPostClicked(file))
                                            }
                                        )
                                    })
                                }

                                composable<PostDetail> { backStackEntry ->
                                    val file = backStackEntry.toRoute<PostDetail>().file
                                    PostDetailPage(
                                        fileName = file,
                                        uiState = postDetailUiState,
                                        loadPostContent = {
                                            onPostDetailEvent(
                                                PostDetailAction.LoadPostDetail(
                                                    it
                                                )
                                            )
                                        },
                                    )
                                }
                            }
                        }
                    }
                    Footer(modifier = Modifier.padding(top = 64.dp, bottom = 32.dp))
                }
                if (appState.shouldAddSideSpace) {
                    Spacer(Modifier.weight(0.25f))
                }
            }
        }
    }

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }
}