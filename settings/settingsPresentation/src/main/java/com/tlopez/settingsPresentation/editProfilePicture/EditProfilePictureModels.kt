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
    val saveButtonState: SaveButtonState = SaveButtonState.UNCHANGED
) : ViewState

enum class SaveButtonState {
    UNCHANGED,
    CHANGED,
    UPLOADING
}