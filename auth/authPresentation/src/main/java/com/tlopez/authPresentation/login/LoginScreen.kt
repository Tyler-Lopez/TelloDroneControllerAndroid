package com.tlopez.authPresentation.login

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
import androidx.compose.ui.unit.dp
import com.tlopez.authPresentation.login.LoginViewEvent.*
import com.tlopez.corePresentation.common.*

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val focusManager = LocalFocusManager.current
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            Title("Tello Share")
            SingleLineOutlinedTextField(
                enabled = buttonsEnabled,
                errorMessage = errorMessageUsername,
                value = textUsername,
                onKeyboardClosed = {
                    focusManager.moveFocus(focusDirection = FocusDirection.Down)
                    viewModel.onEvent(ClosedKeyboardUsername)
                },
                onValueChange = {
                    viewModel.onEvent(TextChangedUsername(it))
                },
                textFieldType = TextFieldType.Username
            )
            SingleLineOutlinedTextField(
                enabled = buttonsEnabled,
                errorMessage = errorMessagePassword,
                value = textPassword,
                onKeyboardClosed = {
                    focusManager.clearFocus()
                    viewModel.onEvent(ClosedKeyboardPassword)
                },
                onValueChange = {
                    viewModel.onEvent(TextChangedPassword(it))
                },
                textFieldType = TextFieldType.Password(isHidden = passHidden) {
                    viewModel.onEvent(ToggledPassVisibility)
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
                    text = "Login",
                ) { viewModel.onEventDebounced(ClickedLogin) }

                TextButton(
                    enabled = buttonsEnabled,
                    modifier = Modifier.defaultMinSize(minWidth = 164.dp),
                    onClick = { viewModel.onEventDebounced(ClickedRegister) }
                ) {
                    Text("Need an account? Register")
                }
            }
        }
    }
}