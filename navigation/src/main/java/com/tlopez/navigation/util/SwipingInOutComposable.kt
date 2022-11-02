package com.tlopez.navigation.util

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.swipingInOutComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    val animationDurationMs = 750

    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            slideIn(animationDurationMs, fromLeft = false)
        },
        exitTransition = { slideOut(animationDurationMs, toLeft = false) },
        popEnterTransition = {
            slideIn(animationDurationMs, fromLeft = true)
        },
        popExitTransition = { slideOut(animationDurationMs, toLeft = true) },
        content = content
    )
}