package com.tlopez.navigation.util

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.selectiveSwipingInOutComposable(
    route: String,
    excludedRouteAnimations: List<String> = emptyList(),
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    val animationDurationMs = 750

    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            initialState.destination.route.let {
                if (it == null || it in excludedRouteAnimations) {
                    EnterTransition.None
                } else {
                    slideIn(animationDurationMs, fromLeft = false)
                }
            }
        },
        exitTransition = {
            initialState.destination.route.let {
                if (it == null || it in excludedRouteAnimations) {
                    ExitTransition.None
                } else {
                    slideOut(animationDurationMs, toLeft = false)
                }
            }
        },
        popEnterTransition = {
            initialState.destination.route.let {
                if (it == null || it in excludedRouteAnimations) {
                    EnterTransition.None
                } else {
                    slideIn(animationDurationMs, fromLeft = true)
                }
            }
        },
        popExitTransition = {
            initialState.destination.route.let {
                if (it == null || it in excludedRouteAnimations) {
                    ExitTransition.None
                } else {
                    slideOut(animationDurationMs, toLeft = true)
                }
            }
        },
        content = content
    )
}