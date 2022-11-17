package com.tlopez.settingsPresentation.settings

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.settingsPresentation.settings.SettingsViewEvent.*

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    AppScaffold(
        text = "Settings",
        onNavigateUp = {
            viewModel.onEvent(ClickedNavigateUp)
        }) {
        ScreenBackground(
            horizontalAlignment = Alignment.Start,
            verticalAlignment = Alignment.Top
        ) {
            TextButton(
                onClick = {
                    viewModel.onEvent(CLickedEditProfilePicture)
                }
            ) {
                Text("Change Profile Picture")
            }
            TextButton(
                onClick = {
                    viewModel.onEvent(ClickedLogout)
                }
            ) {
                Text("Logout")
            }
        }
    }
}