package com.tlopez.tello_controller.presentation.controller_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.ToggleVideo
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewState.*
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewState.ConnectedViewState
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewState.ConnectedViewState.*

@Composable
fun ControllerScreen(viewModel: ControllerViewModel = hiltViewModel()) {
    viewModel.viewState.collectAsState().value?.let {
        if (it is ConnectedViewState) {
            it.latestFrame?.let { bm ->
                Image(
                    bitmap = bm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (it is ConnectedViewState) {
                Switch(
                    checked = it.videoOn,
                    onCheckedChange = {
                        viewModel.onEventDebounced(ToggleVideo)
                    }
                )
            }
            when (it) {
                is DisconnectedIdle -> ControllerDisconnected(eventReceiver = viewModel)
                is DisconnectedError -> ControllerError(eventReceiver = viewModel)
                is Flying -> it.ControllerFlying(eventReceiver = viewModel)
                is ConnectedIdle -> it.ControllerIdle(eventReceiver = viewModel)
                is ConnectedError -> ControllerError(eventReceiver = viewModel)
                is TakingOff -> ControllerTakingOff()
            }
        }
    }
}