package com.tlopez.tello_controller.presentation.loginScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface LoginViewEvent : ViewEvent {

}

data class LoginViewState(
    val textUsername: String,
    val textPassword: String
) : ViewState