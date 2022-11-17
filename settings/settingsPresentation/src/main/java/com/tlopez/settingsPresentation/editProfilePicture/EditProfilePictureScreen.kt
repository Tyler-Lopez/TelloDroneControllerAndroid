package com.tlopez.settingsPresentation.editProfilePicture

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.settingsPresentation.SettingsDestination.*
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewEvent.*

@Composable
fun EditProfilePictureScreen(viewModel: EditProfilePictureViewModel) {
    AppScaffold(
        text = "Profile Picture",
        onNavigateUp = {
            viewModel.onEvent(ClickedNavigateUp)
        }) {
        ScreenBackground {
            Text("hi")
        }
    }
}