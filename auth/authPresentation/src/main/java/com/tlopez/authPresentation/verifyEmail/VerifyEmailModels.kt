package com.tlopez.authPresentation.verifyEmail

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

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
    val errorMessageGeneral: String? = null,
    val errorMessageResend: String? = null,
    val successMessageResend: String? = null,
    val textCode: String = String(),
    val username: String
) : ViewState