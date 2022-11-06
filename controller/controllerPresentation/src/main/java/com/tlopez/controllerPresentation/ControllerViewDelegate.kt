package com.tlopez.controllerPresentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.controllerPresentation.ControllerViewEvent.ToggledVideo
import com.tlopez.controllerPresentation.ControllerViewState.Connected
import com.tlopez.controllerPresentation.ControllerViewState.Disconnected
import com.tlopez.controllerPresentation.composable.BarRow
import com.tlopez.controllerPresentation.composable.BatteryIconText
import com.tlopez.controllerPresentation.composable.VideoIconButton
import com.tlopez.controllerPresentation.subscreens.*
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
            BarRow {
                VideoIconButton(videoOn = state.videoOn) {
                    eventReceiver.onEvent(ToggledVideo)
                }
            }
        },
        bottomBar = {
            BarRow {
                BatteryIconText(batteryPercentage = state.telloState?.batteryPercentage ?: 0)
            }
        }) {
        ScreenBackground {
            when (state) {
                is Connected.ConnectedIdle -> ConnectedIdleScreen(eventReceiver)
                is Connected.Flying -> FlyingScreen(eventReceiver)
                is Connected.Landed -> LandedScreen()
                is Connected.Landing -> LandingScreen()
                is Connected.TakingOff -> TakingOffScreen()
            }
        }
    }
}

@Composable
private fun DisconnectedStateHandler(state: Disconnected) {
    ScreenBackground {
        when (state) {
            Disconnected.DisconnectedError -> DisconnectedErrorScreen()
            Disconnected.DisconnectedIdle -> DisconnectedIdleScreen()
        }
    }
}