package com.tlopez.settingsPresentation.settings

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface SettingsViewEvent : ViewEvent {
    object CLickedEditProfilePicture : SettingsViewEvent
    object ClickedLogout : SettingsViewEvent
    object ClickedNavigateUp : SettingsViewEvent
}

sealed interface SettingsViewState : ViewState {
}