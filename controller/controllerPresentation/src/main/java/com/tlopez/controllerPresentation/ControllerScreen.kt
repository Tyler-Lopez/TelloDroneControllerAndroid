package com.tlopez.controllerPresentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.corePresentation.common.ScreenBackground

@Composable
fun ControllerScreen(viewModel: ControllerViewModel) {
    ScreenBackground {
        Text("Test")
    }
}