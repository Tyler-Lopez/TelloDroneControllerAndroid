package com.tlopez.tello_controller.presentation.controllerScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.tello_controller.architecture.EventReceiver
import com.tlopez.tello_controller.presentation.theme.Typography

@Composable
fun ControllerTakingOff() {
    Text(
        text = "TAKING OFF",
        style = Typography.h1
    )
}