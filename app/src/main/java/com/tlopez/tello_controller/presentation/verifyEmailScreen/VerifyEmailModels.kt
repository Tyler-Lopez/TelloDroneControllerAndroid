package com.tlopez.tello_controller.presentation.verifyEmailScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface VerifyEmailViewEvent : ViewEvent {
    object ClickedVerify : VerifyEmailViewEvent
    data class TextChangedCode(val changedTo: String) : VerifyEmailViewEvent
}

data class VerifyEmailViewState(
    val email: String,
    val textCode: String = String(),
    val username: String
) : ViewState