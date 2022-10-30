package com.tlopez.telloShare.presentation.registerScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.telloShare.presentation.common.ScreenBackground

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            RegisterIdle(
                buttonsEnabled = buttonsEnabled,
                errorMessageEmail = errorMessageEmail,
                errorMessageUsername = errorMessageUsername,
                errorMessagePassword = errorMessagePassword,
                passHidden = passHidden,
                textEmail = textEmail,
                textUsername = textUsername,
                textPassword = textPassword,
                eventReceiver = viewModel
            )
        }
    }
}