package com.tlopez.telloShare.presentation.verifyEmailScreen

import com.tlopez.telloShare.architecture.ViewEvent
import com.tlopez.telloShare.architecture.ViewState

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