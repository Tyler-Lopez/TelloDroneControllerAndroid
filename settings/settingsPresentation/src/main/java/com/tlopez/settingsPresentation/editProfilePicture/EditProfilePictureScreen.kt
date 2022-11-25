package com.tlopez.settingsPresentation.editProfilePicture

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.tlopez.corePresentation.common.AppScaffold
import com.tlopez.corePresentation.common.ProfilePicture
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.corePresentation.common.button.ButtonSize
import com.tlopez.corePresentation.common.button.HighEmphasisButton
import com.tlopez.corePresentation.common.button.MediumEmphasisButton
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewEvent.*
import com.tlopez.settingsPresentation.editProfilePicture.SaveButtonState.*

@Composable
fun EditProfilePictureScreen(viewModel: EditProfilePictureViewModel) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onEvent(SelectedGalleryPicture(it))
        }
    }
    AppScaffold(
        text = "Profile Picture",
        onNavigateUp = {
            viewModel.onEvent(ClickedNavigateUp)
        }) {
        ScreenBackground {
            viewModel.viewState.collectAsState().value?.apply {
                ProfilePicture(fileSpecification = fileSpecification)
                Column {
                    HighEmphasisButton(
                        text = "Select New Photo",
                        enabled = saveButtonState !is Uploading,
                        size = ButtonSize.MEDIUM
                    ) {
                        viewModel.onEvent(ClickedSelectPicture)
                        launcher.launch("image/*")
                    }
                    MediumEmphasisButton(
                        text = when (saveButtonState) {
                            is Uploading -> String()
                            is Saved -> "Saved"
                            else -> "Save"
                        },
                        enabled = saveButtonState is Changed || saveButtonState is Error,
                        size = ButtonSize.MEDIUM,
                        isLoading = saveButtonState is Uploading
                    ) {
                        viewModel.onEvent(ClickedSave)
                    }
                }
            }
        }
    }
}