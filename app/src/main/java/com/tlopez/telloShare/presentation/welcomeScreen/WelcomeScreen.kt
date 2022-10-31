package com.tlopez.telloShare.presentation.welcomeScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tlopez.telloShare.presentation.common.AppScaffold
import com.tlopez.telloShare.presentation.common.ScreenBackground

@Composable
fun WelcomeScreen(viewModel: WelcomeViewModel) {
    AppScaffold(text = "Home") {
        ScreenBackground {
            viewModel.viewState.collectAsState().value?.apply {
                println("here welcome screen")
                Button(onClick = { /*TODO*/ }) {
                    Text("Start Flight")
                }
                Button(onClick = {
                    viewModel.onEventDebounced(WelcomeViewEvent.ClickedLogout)
                }) {
                    Text("Logout")
                }
            }
        }
    }
}