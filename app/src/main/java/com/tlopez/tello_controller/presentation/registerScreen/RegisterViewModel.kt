package com.tlopez.tello_controller.presentation.registerScreen

import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.usecase.RegisterUserUseCase
import com.tlopez.tello_controller.domain.usecase.SignInUserUseCase
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.MainDestination.*
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewEvent.*
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewState.*
import com.tlopez.tello_controller.util.InputValidationUtil
import com.tlopez.tello_controller.util.doOnFailure
import com.tlopez.tello_controller.util.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val signInUserUseCase: SignInUserUseCase,
    private val inputValidationUtil: InputValidationUtil
) : BaseRoutingViewModel<RegisterViewState, RegisterViewEvent, MainDestination>() {

    init {
        RegisterViewState().push()
    }

    override fun onEvent(event: RegisterViewEvent) {
        when (event) {
            is ClickedContinueToApp -> onClickedContinueToApp()
            is ClickedLogin -> onClickedLogin()
            is ClickedRegister -> onClickedRegister()
            is ClosedKeyboardEmail -> onClosedKeyboardEmail()
            is ClosedKeyboardPassword -> onClosedKeyboardPassword()
            is ClosedKeyboardUsername -> onClosedKeyboardUsername()
            is TextChangedEmail -> onTextChangedEmail(event)
            is TextChangedUsername -> onTextChangedUsername(event)
            is TextChangedPassword -> onTextChangedPassword(event)
            is ToggledPassVisibility -> onToggledPassVisibility()
        }
    }

    private fun onClickedContinueToApp() {
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                signInUserUseCase(
                    username = textUsername,
                    password = textPassword
                )
            }
        }
    }

    private fun onClickedLogin() {
        routeTo(NavigateUp)
    }

    private fun onClickedRegister() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleButtonsEnabled()
            lastPushedState?.apply {
                inputValidationUtil.apply {
                    val errMsgEmail = getEmailValidationMessage(textEmail)
                    val errMsgPass = getPasswordValidationMessage(textPassword)
                    val errMsgUsername = getUsernameValidationMessage(textUsername)
                    if (errMsgEmail != null || errMsgPass != null || errMsgUsername != null) {
                        copy(
                            buttonsEnabled = true,
                            errorMessageEmail = errMsgEmail,
                            errorMessagePassword = errMsgPass,
                            errorMessageUsername = errMsgUsername
                        ).push()
                        return@launch
                    }
                }
                signInUserUseCase(textUsername, textPassword)
                    .doOnSuccess {}
                    .doOnFailure {
                        println("here $it")
                    }
            }
            toggleButtonsEnabled()
        }
    }
    /*toggleButtonsEnabled()
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                registerUserUseCase(
                    email = textEmail,
                    username = textUsername,
                    password = textPassword
                ).doOnSuccess {
                    println("here it is success")
                    withContext(Dispatchers.Main) {
                        routeTo(
                            NavigateVerifyEmail(
                                email = textEmail,
                                username = textUsername
                            )
                        )
                    }
                }.doOnFailure {
                    toggleButtonsEnabled()
                }
            }
        }

     */


    private fun onClosedKeyboardEmail() {
        lastPushedState?.apply {
            inputValidationUtil
                .getEmailValidationMessage(textEmail)
                ?.also { copy(errorMessageEmail = it).push() }
        }
    }

    private fun onClosedKeyboardPassword() {
        lastPushedState?.apply {
            inputValidationUtil
                .getPasswordValidationMessage(textPassword)
                ?.also { copy(errorMessagePassword = it).push() }
        }
    }

    private fun onClosedKeyboardUsername() {
        lastPushedState?.apply {
            inputValidationUtil
                .getUsernameValidationMessage(textUsername)
                ?.also { copy(errorMessageUsername = it).push() }
        }
    }

    private fun onTextChangedEmail(event: TextChangedEmail) {
        lastPushedState?.copy(
            errorMessageEmail = null,
            textEmail = event.changedTo
        )?.push()
    }

    private fun onTextChangedUsername(event: TextChangedUsername) {
        lastPushedState?.copy(
            errorMessageUsername = null,
            textUsername = event.changedTo
        )?.push()
    }

    private fun onTextChangedPassword(event: TextChangedPassword) {
        lastPushedState?.copy(
            errorMessagePassword = null,
            textPassword = event.changedTo
        )?.push()
    }

    private fun onToggledPassVisibility() {
        lastPushedState?.run { copy(passHidden = !passHidden) }?.push()
    }

    private fun toggleButtonsEnabled() {
        lastPushedState?.run {
            copy(buttonsEnabled = !buttonsEnabled)
        }?.push()
    }
}