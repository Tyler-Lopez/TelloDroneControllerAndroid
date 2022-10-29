package com.tlopez.tello_controller.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.ui.graphics.vector.ImageVector
import com.tlopez.tello_controller.presentation.Screen

enum class NavigationItem(
    val contentDescription: String,
    val icon: ImageVector,
    val label: String
) {
    HOME(
        contentDescription = "Home",
        icon = Icons.Default.Home,
        label = "Home"
    ),
    FLY(
        contentDescription = "Record Flight",
        icon = Icons.Default.RadioButtonChecked,
        label = "Fly"
    )
}