package com.tlopez.telloShare.presentation.controllerScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.telloShare.architecture.EventReceiver
import com.tlopez.telloShare.presentation.controllerScreen.ControllerViewState.ConnectedViewState

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