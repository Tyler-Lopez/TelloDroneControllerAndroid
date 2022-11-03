package com.tlopez.telloShare.presentation.controllerScreen

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
import com.tlopez.telloShare.presentation.controllerScreen.ControllerViewEvent.ToggleVideo
import com.tlopez.telloShare.presentation.controllerScreen.ControllerViewState.*
import com.tlopez.telloShare.presentation.controllerScreen.ControllerViewState.ConnectedViewState
import com.tlopez.telloShare.presentation.controllerScreen.ControllerViewState.ConnectedViewState.*

@Composable
fun ControllerScreen(viewModel: ControllerViewModelLegacy = hiltViewModel()) {
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
                is DisconnectedIdle -> ControllerDisconnectedIdle(eventReceiver = viewModel)
                is DisconnectedError -> ControllerDisconnectedError(eventReceiver = viewModel)
                is Flying -> it.ControllerFlying(eventReceiver = viewModel)
                is ConnectedIdle -> it.ControllerConnectedIdle(eventReceiver = viewModel)
                is ConnectedError -> ControllerConnectedError(eventReceiver = viewModel)
                is TakingOff -> ControllerTakingOff()
            }
        }
    }
}