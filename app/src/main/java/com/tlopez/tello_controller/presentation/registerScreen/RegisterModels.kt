package com.tlopez.tello_controller.presentation.registerScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface RegisterViewEvent : ViewEvent {
    object ClickedContinueToApp : RegisterViewEvent
    object ClickedRegister : RegisterViewEvent
    data class TextChangedEmail(val changedTo: String): RegisterViewEvent
    data class TextChangedUsername(val changedTo: String): RegisterViewEvent
    data class TextChangedPassword(val changedTo: String): RegisterViewEvent
}

data class RegisterViewState(
    val buttonsEnabled: Boolean = true,
    val successfulRegistration: Boolean = false,
    val textEmail: String = String(),
    val textUsername: String = String(),
    val textPassword: String = String()
) : ViewState