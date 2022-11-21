package com.tlopez.settingsPresentation.editProfilePicture

import androidx.lifecycle.viewModelScope
import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.settingsPresentation.SettingsDestination
import com.tlopez.settingsPresentation.SettingsDestination.*
import com.tlopez.settingsPresentation.editProfilePicture.SaveButtonState.*
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewEvent.*
import com.tlopez.storageDomain.repository.StorageRepository
import com.tlopez.storageDomain.usecase.UpdateUserProfilePicture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfilePictureViewModel @Inject constructor(
    private val updateUserProfilePicture: UpdateUserProfilePicture
) : BaseRoutingViewModel<EditProfilePictureViewState, EditProfilePictureViewEvent, SettingsDestination>() {

    init {
        EditProfilePictureViewState().push()
    }

    override fun onEvent(event: EditProfilePictureViewEvent) {
        when (event) {
            is ClickedNavigateUp -> onClickedNavigateUp()
            is ClickedSave -> onClickedSave()
            is ClickedSelectPicture -> onClickedSelectPicture()
            is SelectedGalleryPicture -> onSelectedGalleryPicture(event)
        }
    }

    private fun onClickedNavigateUp() {
        routeTo(NavigateUp)
    }

    private fun onClickedSave() {
        lastPushedState?.apply {
            copy(saveButtonState = Uploading()).push()
            viewModelScope.launch(Dispatchers.IO) {
                updateUserProfilePicture(
                    fileUri = fileUri!!,
                    onProgressFraction = {}
                )
                    .doOnSuccess {
                        lastPushedState?.copy(
                            saveButtonState = Saved
                        )?.push()
                    }
                    .doOnFailure {
                        lastPushedState?.copy(
                            saveButtonState = Error("An unknown error occurred.")
                        )?.push()
                    }
            }
        }
    }

    private fun onClickedSelectPicture() {
        // No-op
    }

    private fun onSelectedGalleryPicture(event: SelectedGalleryPicture) {
        lastPushedState?.copy(
            fileUri = event.uri,
            saveButtonState = Changed
        )?.push()
    }
}