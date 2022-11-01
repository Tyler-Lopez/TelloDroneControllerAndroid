package com.tlopez.settingsPresentation.settings

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.corePresentation.common.ScreenBackground

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    ScreenBackground {
        Button(onClick = {
            viewModel.onEvent(SettingsViewEvent.ClickedLogout)
        }) {
            Text("Logout")
        }
    }
}