package com.tlopez.authPresentation.login

import androidx.lifecycle.viewModelScope
import com.amazonaws.services.cognitoidentity.model.NotAuthorizedException
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException
import com.amazonaws.services.cognitoidentityprovider.model.UserNotFoundException
import com.tlopez.authDomain.usecase.AuthUseCases
import com.tlopez.authPresentation.AuthDestination
import com.tlopez.authPresentation.AuthDestination.*
import com.tlopez.authPresentation.login.LoginViewEvent.*
import com.tlopez.authPresentation.util.InputValidationUtil
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val inputValidationUtil: InputValidationUtil,
    private val useCases: AuthUseCases
) : BaseRoutingViewModel<LoginViewState, LoginViewEvent, AuthDestination>() {

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
                useCases.signInUser(textUsername, textPassword)
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