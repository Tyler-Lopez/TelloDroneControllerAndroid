package com.tlopez.settingsPresentation.editProfilePicture

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.corePresentation.common.ProfilePicture
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.corePresentation.common.button.ButtonSize
import com.tlopez.corePresentation.common.button.HighEmphasisButton
import com.tlopez.corePresentation.common.button.MediumEmphasisButton
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
            ProfilePicture()
            Column {
                HighEmphasisButton(
                    text = "Select New Photo",
                    size = ButtonSize.MEDIUM
                ) {


                }
                MediumEmphasisButton(
                    text = "Save",
                    enabled = false,
                    size = ButtonSize.MEDIUM
                ) {

                }
            }
        }
    }
}