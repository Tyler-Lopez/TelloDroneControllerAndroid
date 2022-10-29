package com.tlopez.tello_controller.presentation.verifyEmailScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewEvent

sealed interface VerifyEmailViewEvent : ViewEvent {
    object ClickedResendCode : VerifyEmailViewEvent
    object ClickedVerify : VerifyEmailViewEvent
    object ClosedKeyboard : VerifyEmailViewEvent
    data class TextChangedCode(val changedTo: String) : VerifyEmailViewEvent
}

data class VerifyEmailViewState(
    val buttonsEnabled: Boolean = true,
    val email: String?,
    val errorMessageCode: String? = null,
    val textCode: String = String(),
    val username: String
) : ViewState