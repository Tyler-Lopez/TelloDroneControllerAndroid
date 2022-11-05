package com.tlopez.controllerPresentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.tlopez.controllerPresentation.ControllerViewEvent

@Composable
fun VideoIconButton(
    videoOn: Boolean,
    onButtonClicked: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector =
            Icons.Default.Videocam,
            tint = Color.White,
            contentDescription = "Toggle Video"
        )
        Switch(
            checked = videoOn,
            onCheckedChange = { onButtonClicked() },
        )
    }
}