package com.tlopez.settingsPresentation

import com.tlopez.core.architecture.Destination

sealed interface SettingsDestination : Destination {
    data class NavigateEditProfilePicture(val uri: String? = null) : SettingsDestination
    object NavigateLogout : SettingsDestination
    object NavigatePhotoPicker : SettingsDestination
    object NavigateUp : SettingsDestination
}