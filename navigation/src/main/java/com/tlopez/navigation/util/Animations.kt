package com.tlopez.navigation.util

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideIn(
    durationMs: Int,
    fromLeft: Boolean = false,
) =
    slideIntoContainer(
        towards = if (fromLeft) {
            AnimatedContentScope.SlideDirection.End
        } else {
            AnimatedContentScope.SlideDirection.Start
        },
        animationSpec = tween(
            durationMillis = durationMs,
            easing = FastOutSlowInEasing
        )
    )


@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideOut(
    durationMs: Int,
    toLeft: Boolean = false
) =
    slideOutOfContainer(
        towards = if (toLeft) {
            AnimatedContentScope.SlideDirection.End
        } else {
            AnimatedContentScope.SlideDirection.Start
        },
        animationSpec = tween(
            durationMillis = durationMs,
            easing = FastOutSlowInEasing
        )
    )
