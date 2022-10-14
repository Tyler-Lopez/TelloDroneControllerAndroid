package com.tlopez.tello_controller.presentation.controllerScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.tello_controller.architecture.EventReceiver
import com.tlopez.tello_controller.presentation.controllerScreen.ControllerViewState.ConnectedViewState

@Composable
fun ConnectedViewState.ConnectedIdle.ControllerConnectedIdle(eventReceiver: EventReceiver<ControllerViewEvent>) {
    lastFlightMs?.let {
        Text("Last flight was $it long")
    }
    Button(onClick = {
        eventReceiver.onEventDebounced(ControllerViewEvent.ClickedTakeoff)
    }) {
        Text("TAKEOFF")
    }
}