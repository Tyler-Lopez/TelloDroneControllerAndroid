package com.tlopez.telloShare.presentation.loginScreen

import androidx.compose.foundation.layout.*
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
import com.tlopez.telloShare.presentation.common.ActionButton
import com.tlopez.telloShare.presentation.common.SingleLineOutlinedTextField
import com.tlopez.telloShare.presentation.common.ScreenBackground
import com.tlopez.telloShare.presentation.common.TextFieldType
import com.tlopez.telloShare.presentation.loginScreen.LoginViewEvent.*
import com.tlopez.telloShare.presentation.theme.Typography

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val focusManager = LocalFocusManager.current
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            Text(
                text = "Tello Share",
                textAlign = TextAlign.Center,
                style = Typography.h4
            )
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