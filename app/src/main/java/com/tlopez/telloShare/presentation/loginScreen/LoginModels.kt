package com.tlopez.telloShare.presentation.loginScreen

import com.tlopez.telloShare.architecture.ViewEvent
import com.tlopez.telloShare.architecture.ViewState

sealed interface LoginViewEvent : ViewEvent {
    object ClickedLogin : LoginViewEvent
    object ClickedRegister : LoginViewEvent
    object ClosedKeyboardUsername : LoginViewEvent
    object ClosedKeyboardPassword : LoginViewEvent
    data class TextChangedUsername(val changedTo: String) : LoginViewEvent
    data class TextChangedPassword(val changedTo: String) : LoginViewEvent
    object ToggledPassVisibility : LoginViewEvent
}

data class LoginViewState(
    val buttonsEnabled: Boolean = true,
    val errorMessageUsername: String? = null,
    val errorMessagePassword: String? = null,
    val passHidden: Boolean = false,
    val textUsername: String = String(),
    val textPassword: String = String()
) : ViewState