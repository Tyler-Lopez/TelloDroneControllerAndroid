package com.tlopez.tello_controller.presentation.registerScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewEvent

sealed interface RegisterViewEvent : ViewEvent {
    object ClickedContinueToApp : RegisterViewEvent
    object ClickedLogin : RegisterViewEvent
    object ClickedRegister : RegisterViewEvent
    object ClosedKeyboardEmail : RegisterViewEvent
    object ClosedKeyboardUsername : RegisterViewEvent
    object ClosedKeyboardPassword : RegisterViewEvent
    data class TextChangedEmail(val changedTo: String): RegisterViewEvent
    data class TextChangedUsername(val changedTo: String): RegisterViewEvent
    data class TextChangedPassword(val changedTo: String): RegisterViewEvent
    object ToggledPassVisibility : RegisterViewEvent
}

data class RegisterViewState(
    val buttonsEnabled: Boolean = true,
    val errorMessageEmail: String? = null,
    val errorMessageUsername: String? = null,
    val errorMessagePassword: String? = null,
    val passHidden: Boolean = false,
    val textEmail: String = String(),
    val textUsername: String = String(),
    val textPassword: String = String()
) : ViewState