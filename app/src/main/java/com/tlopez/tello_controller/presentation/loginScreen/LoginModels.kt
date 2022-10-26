package com.tlopez.tello_controller.presentation.loginScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface LoginViewEvent : ViewEvent {
    object ClickedRegister : LoginViewEvent
}

data class LoginViewState(
    val textUsername: String = String(),
    val textPassword: String = String()
) : ViewState