package com.tlopez.settingsPresentation.photoPicker

import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.settingsPresentation.SettingsDestination
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewEvent
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoPickerViewModel @Inject constructor(
) : BaseRoutingViewModel<PhotoPickerViewState, PhotoPickerViewEvent, SettingsDestination>() {
    override fun onEvent(event: PhotoPickerViewEvent) {
        TODO("Not yet implemented")
    }
}