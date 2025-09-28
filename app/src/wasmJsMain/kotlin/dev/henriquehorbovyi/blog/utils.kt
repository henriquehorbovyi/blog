package dev.henriquehorbovyi.blog

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun onWindowSizeChanged(
    onCompact: @Composable () -> Unit = {},
    onMedium: @Composable () -> Unit = {},
    onExpanded: @Composable () -> Unit = {}
) {
    val windowSizeClass = calculateWindowSizeClass()
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> onCompact()
        WindowWidthSizeClass.Medium -> onMedium()
        else -> onExpanded()
    }
}

@Composable
inline fun <reified T> Channel<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    noinline action: suspend (T) -> Unit,
) {
    val onAction by rememberUpdatedState(action)
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            receiveAsFlow()
                .flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
                .collectLatest(onAction)
        }
    }
}
