package com.tlopez.settingsPresentation.editProfilePicture

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.tlopez.settingsPresentation.editProfilePicture.SaveButtonState.*
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
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        uri?.toString()?.let {
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
                ProfilePicture(
                    pictureUrl = imageUrl
                )
                Column {
                    HighEmphasisButton(
                        text = "Select New Photo",
                        size = ButtonSize.MEDIUM
                    ) {
                        viewModel.onEvent(ClickedSelectPicture)
                        launcher.launch("image/*")
                    }
                    MediumEmphasisButton(
                        text = "Save",
                        enabled = saveButtonState == CHANGED,
                        size = ButtonSize.MEDIUM
                    ) {
                        viewModel.onEvent(ClickedSave)
                    }
                }
            }
        }
    }
}