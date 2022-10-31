package com.tlopez.telloShare.presentation.registerScreen

import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException.*
import com.tlopez.telloShare.architecture.BaseRoutingViewModel
import com.tlopez.telloShare.domain.usecase.RegisterUserUseCase
import com.tlopez.telloShare.presentation.MainDestination
import com.tlopez.telloShare.presentation.MainDestination.*
import com.tlopez.telloShare.presentation.registerScreen.RegisterViewEvent.*
import com.tlopez.telloShare.util.InputValidationUtil
import com.tlopez.telloShare.util.doOnFailure
import com.tlopez.telloShare.util.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val inputValidationUtil: InputValidationUtil
) : BaseRoutingViewModel<RegisterViewState, RegisterViewEvent, MainDestination>() {

    init {
        RegisterViewState().push()
    }

    override fun onEvent(event: RegisterViewEvent) {
        when (event) {
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
                registerUserUseCase(textEmail, textUsername, textPassword)
                    .doOnSuccess {
                        withContext(Dispatchers.Main) {
                            routeTo(NavigateVerifyEmail(textEmail, textUsername))
                        }
                    }
                    .doOnFailure {
                        when (it) {
                            is UsernameExistsException ->
                                copy(errorMessageUsername = "User already exists").push()
                        }
                    }
            }
            toggleButtonsEnabled()
        }
    }

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