package com.tlopez.settingsPresentation

import com.tlopez.core.architecture.Destination

sealed interface SettingsDestination : Destination {
    data class NavigateEditProfilePicture(val uri: String? = null) : SettingsDestination
    object NavigateLogout : SettingsDestination
    object NavigateUp : SettingsDestination
}