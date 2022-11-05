package com.tlopez.controllerPresentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.controllerPresentation.ControllerViewState.*
import com.tlopez.controllerPresentation.subscreens.ConnectedIdleScreen
import com.tlopez.controllerPresentation.subscreens.DisconnectedIdleScreen
import com.tlopez.controllerPresentation.subscreens.FlyingScreen
import com.tlopez.controllerPresentation.subscreens.TakingOffScreen
import com.tlopez.corePresentation.common.ScreenBackground

@Composable
fun ControllerScreen(viewModel: ControllerViewModel) {
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            when (this) {
                is Disconnected -> DisconnectedStateHandler(this)
                is Connected -> ConnectedStateHandler(this)
            }
        }
    }
}

@Composable
private fun ConnectedStateHandler(state: Connected) {
    when (state) {
        is Connected.ConnectedIdle -> ConnectedIdleScreen()
        is Connected.Flying -> FlyingScreen()
        is Connected.TakingOff -> TakingOffScreen()
    }
}

@Composable
private fun DisconnectedStateHandler(state: Disconnected) {
    when (state) {
        Disconnected.DisconnectedError -> TODO()
        Disconnected.DisconnectedIdle -> TODO()
    }
}