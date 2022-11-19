package com.tlopez.settingsPresentation.editProfilePicture

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface EditProfilePictureViewEvent : ViewEvent {
    object ClickedNavigateUp : EditProfilePictureViewEvent
    object ClickedSelectPicture : EditProfilePictureViewEvent
    data class SelectedGalleryPicture(val uri: String) : EditProfilePictureViewEvent
}

data class EditProfilePictureViewState(
    val imageUrl: String? = null,
) : ViewState {
}