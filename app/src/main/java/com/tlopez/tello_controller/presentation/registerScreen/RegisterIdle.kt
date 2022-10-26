package com.tlopez.tello_controller.presentation.registerScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import com.tlopez.tello_controller.architecture.EventReceiver

@Composable
fun RegisterIdle(
    buttonsEnabled: Boolean,
    textEmail: String,
    textUsername: String,
    textPassword: String,
    eventReceiver: EventReceiver<RegisterViewEvent>
) {
    Text("Email")
    TextField(
        enabled = buttonsEnabled,
        value = textEmail,
        onValueChange = {
            eventReceiver.onEvent(RegisterViewEvent.TextChangedEmail(it))
        }
    )
    Text("Username")
    TextField(
        enabled = buttonsEnabled,
        value = textUsername,
        onValueChange = {
            eventReceiver.onEvent(RegisterViewEvent.TextChangedUsername(it))
        }
    )
    Text("Password")
    TextField(
        enabled = buttonsEnabled,
        value = textPassword,
        onValueChange = {
            eventReceiver.onEvent(RegisterViewEvent.TextChangedPassword(it))
        }
    )
    Button(onClick = { eventReceiver.onEvent(RegisterViewEvent.ClickedRegister) }) {
        Text("Register")
    }
}