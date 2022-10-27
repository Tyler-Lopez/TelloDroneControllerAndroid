package com.tlopez.tello_controller.presentation.loginScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface LoginViewEvent : ViewEvent {
    object ClickedLogin : LoginViewEvent
    object ClickedRegister : LoginViewEvent
    data class TextChangedUsername(val changedTo: String) : LoginViewEvent
    data class TextChangedPassword(val changedTo: String) : LoginViewEvent
}

data class LoginViewState(
    val textUsername: String = String(),
    val textPassword: String = String()
) : ViewState