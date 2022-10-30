package com.tlopez.telloShare.presentation.registerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tlopez.telloShare.architecture.EventReceiver
import com.tlopez.telloShare.presentation.common.ActionButton
import com.tlopez.telloShare.presentation.common.SingleLineOutlinedTextField
import com.tlopez.telloShare.presentation.common.TextFieldType
import com.tlopez.telloShare.presentation.registerScreen.RegisterViewEvent.*
import com.tlopez.telloShare.presentation.theme.Typography

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
        ActionButton(
            isLoading = !buttonsEnabled,
            text = "Register",
        ) { eventReceiver.onEventDebounced(ClickedRegister) }
        TextButton(
            enabled = buttonsEnabled,
            modifier = Modifier.defaultMinSize(minWidth = 164.dp),
            onClick = { eventReceiver.onEventDebounced(ClickedLogin) }
        ) {
            Text("Have an account? Login")
        }
    }
}