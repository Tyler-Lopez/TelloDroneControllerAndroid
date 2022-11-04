package com.tlopez.authPresentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tlopez.corePresentation.common.*
import com.tlopez.corePresentation.theme.Typography

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
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
                    viewModel.onEvent(RegisterViewEvent.ClosedKeyboardUsername)
                },
                onValueChange = {
                    viewModel.onEvent(RegisterViewEvent.TextChangedUsername(it))
                },
                textFieldType = TextFieldType.Username
            )
            SingleLineOutlinedTextField(
                enabled = buttonsEnabled,
                errorMessage = errorMessageEmail,
                value = textEmail,
                onKeyboardClosed = {
                    focusManager.moveFocus(focusDirection = FocusDirection.Down)
                    viewModel.onEvent(RegisterViewEvent.ClosedKeyboardEmail)
                },
                onValueChange = {
                    viewModel.onEvent(RegisterViewEvent.TextChangedEmail(it))
                },
                textFieldType = TextFieldType.Email
            )
            SingleLineOutlinedTextField(
                enabled = buttonsEnabled,
                errorMessage = errorMessagePassword,
                value = textPassword,
                onKeyboardClosed = {
                    focusManager.clearFocus()
                    viewModel.onEvent(RegisterViewEvent.ClosedKeyboardPassword)
                },
                onValueChange = {
                    viewModel.onEvent(RegisterViewEvent.TextChangedPassword(it))
                },
                textFieldType = TextFieldType.Password(isHidden = passHidden) {
                    viewModel.onEvent(RegisterViewEvent.ToggledPassVisibility)
                }
            )
            errorMessageGeneral?.let {
                TextError(it)
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButton(
                    isLoading = !buttonsEnabled,
                    text = "Register",
                ) { viewModel.onEventDebounced(RegisterViewEvent.ClickedRegister) }
                TextButton(
                    enabled = buttonsEnabled,
                    modifier = Modifier.defaultMinSize(minWidth = 164.dp),
                    onClick = { viewModel.onEventDebounced(RegisterViewEvent.ClickedLogin) }
                ) {
                    Text("Have an account? Login")
                }
            }
        }
    }
}