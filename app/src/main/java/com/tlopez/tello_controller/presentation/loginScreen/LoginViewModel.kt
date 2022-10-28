package com.tlopez.tello_controller.presentation.loginScreen

import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthException.*
import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.repository.AuthRepository
import com.tlopez.tello_controller.domain.usecase.SignInUserUseCase
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.MainDestination.*
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewEvent.*
import com.tlopez.tello_controller.presentation.welcomeScreen.WelcomeViewEvent
import com.tlopez.tello_controller.presentation.welcomeScreen.WelcomeViewState
import com.tlopez.tello_controller.util.InputValidationUtil
import com.tlopez.tello_controller.util.doOnFailure
import com.tlopez.tello_controller.util.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase,
    private val inputValidationUtil: InputValidationUtil,
) : BaseRoutingViewModel<LoginViewState, LoginViewEvent, MainDestination>() {

    init {
        LoginViewState().push()
    }

    override fun onEvent(event: LoginViewEvent) {
        when (event) {
            is ClickedLogin -> onClickedLogin()
            is ClickedRegister -> onClickedRegister()
            is ClosedKeyboardUsername -> onClosedKeyboardUsername()
            is ClosedKeyboardPassword -> onClosedKeyboardPassword()
            is TextChangedPassword -> onTextChangedPassword(event)
            is TextChangedUsername -> onTextChangedUsername(event)
            is ToggledPassVisibility -> onToggledPassVisibility()
        }
    }

    private fun onClickedLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                copy(buttonsEnabled = false).push()
                val errorMessagePassword = inputValidationUtil
                    .getPasswordValidationMessage(password = textPassword)
                val errorMessageUsername = inputValidationUtil
                    .getUsernameValidationMessage(username = textUsername)
                if (errorMessagePassword != null || errorMessageUsername != null) {
                    copy(
                        buttonsEnabled = true,
                        errorMessagePassword = errorMessagePassword,
                        errorMessageUsername = errorMessageUsername
                    ).push()
                    return@launch
                }
                signInUserUseCase(textUsername, textPassword)
                    .doOnSuccess {}
                    .doOnFailure {
                        when (it) {
                            is UserNotFoundException ->
                                copy(errorMessageUsername = "User does not exist").push()
                            is NotAuthorizedException ->
                                copy(errorMessagePassword = "Invalid password").push()
                        }
                    }
            }
        }
        lastPushedState?.copy(buttonsEnabled = true)?.push()
    }

    private fun onClickedRegister() {
        routeTo(NavigateRegister)
    }

    private fun onClosedKeyboardUsername() {
        lastPushedState?.apply {
            inputValidationUtil
                .getUsernameValidationMessage(textUsername)
                ?.also {
                    copy(errorMessageUsername = it).push()
                }
        }
    }

    private fun onClosedKeyboardPassword() {
        lastPushedState?.apply {
            inputValidationUtil
                .getPasswordValidationMessage(textPassword)
                ?.also {
                    copy(errorMessagePassword = it).push()
                }
        }
    }

    private fun onTextChangedPassword(event: TextChangedPassword) {
        lastPushedState?.copy(
            errorMessagePassword = null,
            textPassword = event.changedTo
        )?.push()
    }

    private fun onTextChangedUsername(event: TextChangedUsername) {
        lastPushedState?.copy(
            errorMessageUsername = null,
            textUsername = event.changedTo
        )?.push()
    }

    private fun onToggledPassVisibility() {
        lastPushedState?.run {
            copy(passHidden = !passHidden)
        }?.push()
    }
}