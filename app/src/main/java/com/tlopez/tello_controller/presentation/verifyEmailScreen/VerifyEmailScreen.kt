package com.tlopez.tello_controller.presentation.verifyEmailScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.tello_controller.presentation.Screen
import com.tlopez.tello_controller.presentation.common.ScreenBackground
import com.tlopez.tello_controller.presentation.verifyEmailScreen.VerifyEmailViewEvent.*

@Composable
fun VerifyEmailScreen(viewModel: VerifyEmailViewModel) {
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            Text("Successfully Registered")
            Text("Text email is $email")
            Text("Text user is $username")
            Text("E-mail verification is required, please check your email and input code below")
            TextField(
                value = textCode,
                onValueChange = { viewModel.onEvent(TextChangedCode(it)) }
            )
            Button(onClick = {
                viewModel.onEventDebounced(ClickedVerify)
            })
            {
                Text("Verify")
            }
        }
    }
}