package com.tlopez.controllerPresentation.subscreens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.controllerDomain.TelloState
import com.tlopez.controllerPresentation.ControllerViewEvent
import com.tlopez.core.architecture.EventReceiver

@Composable
fun ConnectedIdleScreen(
    eventReceiver: EventReceiver<ControllerViewEvent>
) {
    Text("Connected")
}