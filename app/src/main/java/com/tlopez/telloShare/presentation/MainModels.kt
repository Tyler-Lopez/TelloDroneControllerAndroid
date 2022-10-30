package com.tlopez.telloShare.presentation

import com.tlopez.telloShare.architecture.Destination
import com.tlopez.telloShare.architecture.ViewEvent
import com.tlopez.telloShare.architecture.ViewState

sealed interface MainDestination : Destination {
    object NavigateController : MainDestination
    object NavigateRegister : MainDestination
    object NavigateLogin : MainDestination
    data class NavigateVerifyEmail(
        val email: String?,
        val username: String
    ) : MainDestination

    object NavigateWelcome : MainDestination
    object NavigateUp : MainDestination
}

sealed interface MainViewEvent : ViewEvent {

}

sealed interface MainViewState : ViewState {
    object Authenticated : MainViewState
    object Unauthenticated : MainViewState
}