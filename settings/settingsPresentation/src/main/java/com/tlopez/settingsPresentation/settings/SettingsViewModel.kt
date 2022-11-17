package com.tlopez.settingsPresentation.settings

import androidx.lifecycle.viewModelScope
import com.tlopez.authDomain.usecase.LogoutUser
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.settingsPresentation.SettingsDestination
import com.tlopez.settingsPresentation.SettingsDestination.*
import com.tlopez.settingsPresentation.settings.SettingsViewEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUser: LogoutUser
) : BaseRoutingViewModel<SettingsViewState, SettingsViewEvent, SettingsDestination>() {

    override fun onEvent(event: SettingsViewEvent) {
        when (event) {
            is CLickedEditProfilePicture -> onClickedEditProfilePicture()
            is ClickedLogout -> onClickedLogout()
            is ClickedNavigateUp -> onClickedNavigateUp()
        }
    }

    private fun onClickedEditProfilePicture() {
        routeTo(NavigateEditProfile)
    }

    private fun onClickedLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUser()
                .doOnSuccess {
                    withContext(Dispatchers.Main) {
                        routeTo(NavigateLogout)
                    }
                }
        }
    }

    private fun onClickedNavigateUp() {
        routeTo(NavigateUp)
    }
}