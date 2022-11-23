package com.tlopez.settingsPresentation.editProfilePicture

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.corePresentation.common.FileSpecification
import com.tlopez.settingsPresentation.SettingsDestination
import com.tlopez.settingsPresentation.SettingsDestination.NavigateUp
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewEvent.*
import com.tlopez.settingsPresentation.editProfilePicture.SaveButtonState.*
import com.tlopez.storageDomain.usecase.GetUserProfilePictureUrl
import com.tlopez.storageDomain.usecase.UpdateAuthenticatedUserProfilePicture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfilePictureViewModel @Inject constructor(
    private val getUser: GetUser,
    private val getUserProfilePictureUrl: GetUserProfilePictureUrl,
    private val updateUserProfilePicture: UpdateAuthenticatedUserProfilePicture
) : BaseRoutingViewModel<EditProfilePictureViewState, EditProfilePictureViewEvent, SettingsDestination>() {

    init {
        EditProfilePictureViewState().push()
        viewModelScope.launch(Dispatchers.IO) {
            val username = getUser().getOrThrow().username
            getUserProfilePictureUrl(username)
                .doOnSuccess {
                    println("Here, $it")
                    lastPushedState?.copy(
                        fileSpecification = FileSpecification(
                            fileKey = username,
                            fileUrl = it
                        )
                    )?.push()
                }
        }
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
                    fileUri = Uri.parse(fileSpecification?.fileUrl),
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
            fileSpecification = FileSpecification(fileUrl = event.uri.toString()),
            saveButtonState = Changed
        )?.push()
    }
}