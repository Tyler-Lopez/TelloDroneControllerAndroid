package com.tlopez.tello_controller.presentation.welcomeScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.tello_controller.presentation.common.ScreenBackground

@Composable
fun WelcomeScreen(viewModel: WelcomeViewModel) {
    ScreenBackground {
        viewModel.viewState.collectAsState().value?.apply {
            Button(onClick = { /*TODO*/ }) {
                Text("Start Flight")
            }
        }
    }
}