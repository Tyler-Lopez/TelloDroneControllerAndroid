package com.tlopez.tello_controller.presentation.registerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewEvent.*

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.viewState.collectAsState().value?.apply {
            TextField(
                value = textUsername,
                onValueChange = {
                    viewModel.onEvent(TextChangedUsername(it))
                }
            )
            TextField(
                value = textPassword,
                onValueChange = {
                    viewModel.onEvent(TextChangedPassword(it))
                }
            )
        }
    }
}