package com.tlopez.settingsPresentation

import com.tlopez.core.architecture.Destination

sealed interface SettingsDestination : Destination {
    object NavigateEditProfile : SettingsDestination
    object NavigateLogout : SettingsDestination
    object NavigateUp : SettingsDestination
}