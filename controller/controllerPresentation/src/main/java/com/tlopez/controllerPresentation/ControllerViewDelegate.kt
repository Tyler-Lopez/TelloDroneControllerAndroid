package com.tlopez.controllerPresentation

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.tlopez.controllerPresentation.ControllerViewEvent.ClickedTakePicture
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ConnectedStateHandler(
    state: Connected,
    eventReceiver: EventReceiver<ControllerViewEvent>
) {
    val permissionsState = rememberPermissionState(
        permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    Scaffold(
        topBar = {
            BarRow {
                VideoIconButton(videoOn = state.videoOn) {
                    eventReceiver.onEvent(ToggledVideo)
                }
                if (state.videoOn) {
                    IconButton(onClick = {
                        when {
                            permissionsState.hasPermission -> {
                                eventReceiver.onEventDebounced(ClickedTakePicture)
                            }
                            permissionsState.shouldShowRationale -> {
                                permissionsState.launchPermissionRequest()
                                println("Should show rationale")
                            }
                            else -> {
                                println("Shouldn't show rationale, perma-denied rip")
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            tint = Color.White,
                            contentDescription = "Take a picture"
                        )
                    }
                }
            }

        },
        bottomBar = {
            BarRow {
                BatteryIconText(batteryPercentage = state.telloState?.batteryPercentage ?: 0)
            }
        }) {
        ScreenBackground(
            padding = 0.dp,
            scrollEnabled = false
        ) {
            Box {
                state.bitmapLatest?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )
                }
                Column {
                    when (state) {
                        is Connected.ConnectedIdle -> ConnectedIdleScreen(eventReceiver)
                        is Connected.Flying -> FlyingScreen(
                            state.thumbstickStateRollPitch,
                            state.thumbstickStateThrottleYaw,
                            eventReceiver
                        )
                        is Connected.Landed -> LandedScreen()
                        is Connected.Landing -> LandingScreen()
                        is Connected.TakingOff -> TakingOffScreen()
                    }
                }
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