package com.tlopez.settingsPresentation.editProfilePicture

import androidx.lifecycle.viewModelScope
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.settingsPresentation.SettingsDestination
import com.tlopez.settingsPresentation.SettingsDestination.*
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewEvent.*
import com.tlopez.storageDomain.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfilePictureViewModel @Inject constructor(
    private val storageRepository: StorageRepository
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
            copy(saveButtonState = SaveButtonState.UPLOADING).push()
            viewModelScope.launch(Dispatchers.IO) {
                storageRepository.uploadFile(
                    "TEST",
                    fileUri!!,
                    {}
                )
            }
        }
    }

    private fun onClickedSelectPicture() {
        // No-op
    }

    private fun onSelectedGalleryPicture(event: SelectedGalleryPicture) {
        lastPushedState?.copy(
            fileUri = event.uri,
            saveButtonState = SaveButtonState.CHANGED
        )?.push()
    }
}