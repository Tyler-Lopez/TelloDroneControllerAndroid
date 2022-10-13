package com.tlopez.tello_controller.presentation.controllerScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.tello_controller.architecture.EventReceiver

@Composable
fun ControllerError(eventReceiver: EventReceiver<ControllerViewEvent>) {
    Text("There was an error")
}