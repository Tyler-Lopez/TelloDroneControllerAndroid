package com.tlopez.telloShare.presentation.loginScreen

import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException.*
import com.tlopez.telloShare.architecture.BaseRoutingViewModel
import com.tlopez.telloShare.domain.usecase.SignInUserUseCase
import com.tlopez.telloShare.presentation.MainDestination
import com.tlopez.telloShare.presentation.MainDestination.*
import com.tlopez.telloShare.presentation.loginScreen.LoginViewEvent.*
import com.tlopez.telloShare.util.InputValidationUtil
import com.tlopez.telloShare.util.doOnFailure
import com.tlopez.telloShare.util.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                    .doOnSuccess {
                        withContext(Dispatchers.Main) {
                            routeTo(NavigateWelcome)
                        }
                    }
                    .doOnFailure {
                        when (it) {
                            is UserNotFoundException ->
                                copy(errorMessageUsername = "User does not exist").push()
                            is NotAuthorizedException ->
                                copy(errorMessagePassword = "Invalid password").push()
                            is UserNotConfirmedException -> {
                                lastPushedState?.copy(buttonsEnabled = true)?.push()
                                withContext(Dispatchers.Main) {
                                    routeTo(
                                        NavigateVerifyEmail(
                                            email = null,
                                            username = textUsername
                                        )
                                    )
                                }
                            }
                        }
                    }
            }
        }
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