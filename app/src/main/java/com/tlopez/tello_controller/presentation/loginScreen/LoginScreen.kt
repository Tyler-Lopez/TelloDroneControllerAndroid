package com.tlopez.tello_controller.presentation.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tlopez.tello_controller.presentation.common.ScreenBackground
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewEvent.*

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            Text("Username")
            TextField(
              //  enabled = buttonsEnabled,
                value = textUsername,
                onValueChange = {
                    viewModel.onEvent(TextChangedUsername(it))
                }
            )
            Text("Password")
            TextField(
                //enabled = buttonsEnabled,
                value = textPassword,
                onValueChange = {
                    viewModel.onEvent(TextChangedPassword(it))
                }
            )
            Button(
                onClick = { viewModel.onEventDebounced(ClickedLogin) }
            ) {
                Text("login")
            }
            Button(
                onClick = { viewModel.onEventDebounced(ClickedRegister) }
            ) {
                Text("Goto register")
            }
        }
    }
}