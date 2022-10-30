package com.tlopez.telloShare.presentation.controllerScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.telloShare.architecture.EventReceiver

@Composable
fun ControllerDisconnectedError(eventReceiver: EventReceiver<ControllerViewEvent>) {
    Text("Error while disconnected")
}