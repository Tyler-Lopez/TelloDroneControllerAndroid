package com.tlopez.settingsPresentation.settings

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.corePresentation.common.ScreenBackground

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    AppScaffold(
        text = "Settings",
        onNavigateUp = {
            viewModel.onEvent(
                SettingsViewEvent.ClickedNavigateUp
            )
        }) {
        ScreenBackground {
            Button(onClick = {
                viewModel.onEvent(SettingsViewEvent.ClickedLogout)
            }) {
                Text("Logout")
            }
        }
    }
}