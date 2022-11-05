package com.tlopez.controllerPresentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tlopez.controllerPresentation.ControllerViewEvent.*
import com.tlopez.controllerPresentation.ControllerViewState.*
import com.tlopez.controllerPresentation.subscreens.ConnectedIdleScreen
import com.tlopez.controllerPresentation.subscreens.DisconnectedIdleScreen
import com.tlopez.controllerPresentation.subscreens.FlyingScreen
import com.tlopez.controllerPresentation.subscreens.TakingOffScreen
import com.tlopez.core.architecture.EventReceiver
import com.tlopez.corePresentation.common.ScreenBackground

@Composable
fun ControllerScreen(viewModel: ControllerViewModel) {
    viewModel.viewState.collectAsState().value?.apply {
        when (this) {
            is Disconnected -> DisconnectedStateHandler(this)
            is Connected -> ConnectedStateHandler(this, viewModel)
        }
    }
}

@Composable
private fun ConnectedStateHandler(
    state: Connected,
    eventReceiver: EventReceiver<ControllerViewEvent>
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0.1f, 0.1f, 0.1f, 0.1f))
            ) {
                IconToggleButton(
                    checked = state.videoOn,
                    onCheckedChange = {
                        eventReceiver.onEvent(ToggledVideo)
                    }
                ) {

                }

            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0.1f, 0.1f, 0.1f, 0.1f))
            ) {

            }
        }) {

        Text(text = "${state.telloState}")
        when (state) {
            is Connected.ConnectedIdle -> ConnectedIdleScreen(eventReceiver)
            is Connected.Flying -> FlyingScreen()
            is Connected.TakingOff -> TakingOffScreen()
        }
    }
}

@Composable
private fun DisconnectedStateHandler(state: Disconnected) {
    ScreenBackground {
        when (state) {
            Disconnected.DisconnectedError -> {}
            Disconnected.DisconnectedIdle -> DisconnectedIdleScreen()
        }
    }
}