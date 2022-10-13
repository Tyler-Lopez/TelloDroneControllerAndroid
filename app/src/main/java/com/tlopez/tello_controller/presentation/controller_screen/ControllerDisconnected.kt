package com.tlopez.tello_controller.presentation.controller_screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.tello_controller.architecture.EventReceiver

@Composable
fun ControllerDisconnected(eventReceiver: EventReceiver<ControllerViewEvent>) {
    Button(onClick = {
        eventReceiver.onEventDebounced(ControllerViewEvent.ClickedConnect)
    }) {
        Text("CONNECT")
    }
}