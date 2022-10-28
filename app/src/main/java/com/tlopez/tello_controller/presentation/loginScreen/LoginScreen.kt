package com.tlopez.tello_controller.presentation.loginScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tlopez.tello_controller.presentation.common.CustomOutlinedTextField
import com.tlopez.tello_controller.presentation.common.ScreenBackground
import com.tlopez.tello_controller.presentation.common.TextFieldType
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewEvent.*
import com.tlopez.tello_controller.presentation.theme.Typography

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            Text(
                text = "Tello Controller",
                textAlign = TextAlign.Center,
                style = Typography.h4
            )
            CustomOutlinedTextField(
                enabled = buttonsEnabled,
                errorMessage = errorMessageUsername,
                value = textUsername,
                onValueChange = {
                    viewModel.onEvent(TextChangedUsername(it))
                },
                textFieldType = TextFieldType.Username
            )
            CustomOutlinedTextField(
                enabled = buttonsEnabled,
                errorMessage = errorMessagePassword,
                value = textPassword,
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
                Button(
                    modifier = Modifier.defaultMinSize(minWidth = 164.dp),
                    onClick = { viewModel.onEventDebounced(ClickedLogin) }
                ) {
                    Text("Login")
                }
                TextButton(
                    modifier = Modifier.defaultMinSize(minWidth = 164.dp),
                    onClick = { viewModel.onEventDebounced(ClickedRegister) }
                ) {
                    Text("Need an Account? Register")
                }
            }
        }
    }
}