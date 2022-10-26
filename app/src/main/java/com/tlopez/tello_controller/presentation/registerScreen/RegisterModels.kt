package com.tlopez.tello_controller.presentation.registerScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface RegisterViewEvent : ViewEvent {
    data class TextChangedUsername(val changedTo: String): RegisterViewEvent
    data class TextChangedPassword(val changedTo: String): RegisterViewEvent
}

data class RegisterViewState(
    val textUsername: String = String(),
    val textPassword: String = String()
) : ViewState