package com.tlopez.tello_controller.presentation.registerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tlopez.tello_controller.architecture.EventReceiver
import com.tlopez.tello_controller.presentation.common.SingleLineOutlinedTextField
import com.tlopez.tello_controller.presentation.common.TextFieldType
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewEvent.*
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewEvent
import com.tlopez.tello_controller.presentation.theme.Typography

@Composable
fun RegisterIdle(
    buttonsEnabled: Boolean,
    errorMessageEmail: String?,
    errorMessageUsername: String?,
    errorMessagePassword: String?,
    passHidden: Boolean,
    textEmail: String,
    textUsername: String,
    textPassword: String,
    eventReceiver: EventReceiver<RegisterViewEvent>
) {
    val focusManager = LocalFocusManager.current
    Text(
        text = "Create Account",
        textAlign = TextAlign.Center,
        style = Typography.h4
    )
    SingleLineOutlinedTextField(
        enabled = buttonsEnabled,
        errorMessage = errorMessageUsername,
        value = textUsername,
        onKeyboardClosed = {
            focusManager.moveFocus(focusDirection = FocusDirection.Down)
            eventReceiver.onEvent(ClosedKeyboardUsername)
        },
        onValueChange = {
            eventReceiver.onEvent(TextChangedUsername(it))
        },
        textFieldType = TextFieldType.Username
    )
    SingleLineOutlinedTextField(
        enabled = buttonsEnabled,
        errorMessage = errorMessageEmail,
        value = textEmail,
        onKeyboardClosed = {
            focusManager.moveFocus(focusDirection = FocusDirection.Down)
            eventReceiver.onEvent(ClosedKeyboardEmail)
        },
        onValueChange = {
            eventReceiver.onEvent(TextChangedEmail(it))
        },
        textFieldType = TextFieldType.Email
    )
    SingleLineOutlinedTextField(
        enabled = buttonsEnabled,
        errorMessage = errorMessagePassword,
        value = textPassword,
        onKeyboardClosed = {
            focusManager.clearFocus()
            eventReceiver.onEvent(ClosedKeyboardPassword)
        },
        onValueChange = {
            eventReceiver.onEvent(TextChangedPassword(it))
        },
        textFieldType = TextFieldType.Password(isHidden = passHidden) {
            eventReceiver.onEvent(ToggledPassVisibility)
        }
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            enabled = buttonsEnabled,
            modifier = Modifier.defaultMinSize(minWidth = 164.dp),
            onClick = { eventReceiver.onEventDebounced(ClickedRegister) }
        ) {
            Text("Register")
        }
        TextButton(
            enabled = buttonsEnabled,
            modifier = Modifier.defaultMinSize(minWidth = 164.dp),
            onClick = { eventReceiver.onEventDebounced(ClickedLogin) }
        ) {
            Text("Have an account? Login")
        }
    }
}