package com.tlopez.tello_controller.presentation.controller_screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.presentation.Greeting
import com.tlopez.tello_controller.util.TelloCommand

@Composable
fun ControllerScreen(viewModel: ControllerViewModel = hiltViewModel()) {
    Column {
        Greeting("Android")
        Button(onClick = {
            viewModel.onEvent(ControllerViewEvent.ClickedConnect)
        }) {
            Text("start service")
        }
        Button(onClick = {
            viewModel.onEvent(ControllerViewEvent.ClickedTakeoff)

        }) {
            Text("send takeoff command")
        }
        Button(onClick = {
            viewModel.onEvent(ControllerViewEvent.ClickedLand)

        }) {
            Text("send land command")
        }
    }
}