package com.tlopez.controllerPresentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.controllerPresentation.ControllerViewState.*
import com.tlopez.controllerPresentation.subscreens.ConnectedIdleScreen
import com.tlopez.controllerPresentation.subscreens.DisconnectedIdleScreen

@Composable
fun ControllerScreen(viewModel: ControllerViewModel) {
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            when (this) {
                is DisconnectedIdle -> DisconnectedIdleScreen()
                is ConnectedIdle -> ConnectedIdleScreen()
                else -> {}
            }
        }
    }
}