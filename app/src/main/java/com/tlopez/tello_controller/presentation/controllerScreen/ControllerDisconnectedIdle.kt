package com.tlopez.tello_controller.presentation.controllerScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.tello_controller.architecture.EventReceiver

@Composable
fun ControllerDisconnectedIdle(eventReceiver: EventReceiver<ControllerViewEvent>) {
    Button(onClick = {
        eventReceiver.onEventDebounced(ControllerViewEvent.ClickedConnect)
    }) {
        Text("CONNECT")
    }
}