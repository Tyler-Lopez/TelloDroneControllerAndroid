package com.tlopez.tello_controller.presentation.controller_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ControllerScreen(viewModel: ControllerViewModel = hiltViewModel()) {
    viewModel.viewState.collectAsState().value?.apply {
        Column {
            Button(onClick = {
                viewModel.onEvent(ClickedConnect)
            }) {
                Text("start service")
            }
            Button(onClick = {
                viewModel.onEvent(ClickedTakeoff)

            }) {
                Text("send takeoff command")
            }
            Button(onClick = {
                viewModel.onEvent(ClickedLand)
            }) {
                Text("send land command")
            }
            Text(telloState.toString())
        }
    }
}