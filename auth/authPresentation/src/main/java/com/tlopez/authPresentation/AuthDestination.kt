package com.tlopez.authPresentation

import com.tlopez.core.architecture.Destination

sealed interface AuthDestination : Destination {
    object NavigateLogin : AuthDestination
    object NavigateRegister : AuthDestination
    data class NavigateVerifyEmail(
        val email: String?,
        val username: String
    ) : AuthDestination
    object NavigateWelcome : AuthDestination
    object NavigateUp : AuthDestination
}