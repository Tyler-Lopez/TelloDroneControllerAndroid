package com.tlopez.settingsPresentation.editProfilePicture

import android.net.Uri
import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface EditProfilePictureViewEvent : ViewEvent {
    object ClickedNavigateUp : EditProfilePictureViewEvent
    object ClickedSave : EditProfilePictureViewEvent
    object ClickedSelectPicture : EditProfilePictureViewEvent
    data class SelectedGalleryPicture(val uri: Uri) : EditProfilePictureViewEvent
}

data class EditProfilePictureViewState(
    val fileUri: Uri? = null,
    val saveButtonState: SaveButtonState = SaveButtonState.Unchanged
) : ViewState

sealed interface SaveButtonState {
    object Changed : SaveButtonState
    data class Error(val reason: String) : SaveButtonState
    object Saved : SaveButtonState
    object Unchanged : SaveButtonState
    data class Uploading(val progressFraction: Double = 0.0) : SaveButtonState
}