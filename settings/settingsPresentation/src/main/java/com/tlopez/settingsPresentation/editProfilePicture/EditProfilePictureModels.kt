package com.tlopez.settingsPresentation.editProfilePicture

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface EditProfilePictureViewEvent : ViewEvent {
    object ClickedNavigateUp : EditProfilePictureViewEvent
}

sealed interface EditProfilePictureViewState : ViewState {

}