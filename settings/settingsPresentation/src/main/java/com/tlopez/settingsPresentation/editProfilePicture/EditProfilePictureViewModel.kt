package com.tlopez.settingsPresentation.editProfilePicture

import com.tlopez.authDomain.usecase.LogoutUser
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.settingsPresentation.SettingsDestination
import com.tlopez.settingsPresentation.SettingsDestination.*
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewEvent.*
import com.tlopez.settingsPresentation.settings.SettingsViewEvent
import com.tlopez.settingsPresentation.settings.SettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfilePictureViewModel @Inject constructor(
) : BaseRoutingViewModel<EditProfilePictureViewState, EditProfilePictureViewEvent, SettingsDestination>() {

    override fun onEvent(event: EditProfilePictureViewEvent) {
        when (event) {
            is ClickedNavigateUp -> onClickedNavigateUp()
        }
    }

    private fun onClickedNavigateUp() {
        routeTo(NavigateUp)
    }
}