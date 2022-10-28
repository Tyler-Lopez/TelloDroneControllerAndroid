package com.tlopez.tello_controller.presentation

import com.tlopez.tello_controller.architecture.Destination
import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface MainDestination : Destination {
    object NavigateController : MainDestination
    object NavigateEnterName : MainDestination
    object NavigateRegister : MainDestination
    data class NavigateVerifyEmail(
        val email: String,
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