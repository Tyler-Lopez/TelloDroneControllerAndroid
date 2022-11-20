package com.tlopez.settingsPresentation.editProfilePicture

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface EditProfilePictureViewEvent : ViewEvent {
    object ClickedNavigateUp : EditProfilePictureViewEvent
    object ClickedSave : EditProfilePictureViewEvent
    object ClickedSelectPicture : EditProfilePictureViewEvent
    data class SelectedGalleryPicture(val uri: String) : EditProfilePictureViewEvent
}

data class EditProfilePictureViewState(
    val imageUrl: String? = null,
    val saveButtonState: SaveButtonState = SaveButtonState.UNCHANGED
) : ViewState

enum class SaveButtonState {
    UNCHANGED,
    CHANGED,
    UPLOADING
}