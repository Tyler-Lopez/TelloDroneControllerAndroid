package com.tlopez.tello_controller.presentation.registerScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.tello_controller.architecture.EventReceiver

@Composable
fun RegisterSuccess(
    textEmail: String,
    textUsername: String,
    eventReceiver: EventReceiver<RegisterViewEvent>
) {
    Text("Successfully registered $textEmail")
    Button({
        eventReceiver.onEventDebounced(
            RegisterViewEvent.ClickedContinueToApp
        )
    }) {
        Text("Continue")
    }

}