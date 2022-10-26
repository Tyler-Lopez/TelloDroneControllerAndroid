package com.tlopez.tello_controller.presentation.registerScreen

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.usecase.RegisterUserUseCase
import com.tlopez.tello_controller.domain.usecase.SignInUserUseCase
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val signInUserUseCase: SignInUserUseCase
) : BaseRoutingViewModel<RegisterViewState, RegisterViewEvent, MainDestination>() {

    init {
        RegisterViewState().push()
    }

    override fun onEvent(event: RegisterViewEvent) {
        when (event) {
            is ClickedContinueToApp -> onClickedContinueToApp()
            is ClickedRegister -> onClickedRegister()
            is TextChangedEmail -> onTextChangedEmail(event)
            is TextChangedUsername -> onTextChangedUsername(event)
            is TextChangedPassword -> onTextChangedPassword(event)
        }
    }

    private fun onClickedContinueToApp() {
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                signInUserUseCase(
                    textUsername,
                    textPassword
                )
            }
        }
    }

    private fun onClickedRegister() {
        toggleButtonsEnabled()
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                registerUserUseCase(
                    email = textEmail,
                    username = textUsername,
                    password = textPassword
                )
                toggleButtonsEnabled()
            }
        }
    }

    private fun onTextChangedEmail(event: TextChangedEmail) {
        lastPushedState?.copy(textEmail = event.changedTo)?.push()
    }

    private fun onTextChangedUsername(event: TextChangedUsername) {
        lastPushedState?.copy(textUsername = event.changedTo)?.push()
    }

    private fun onTextChangedPassword(event: TextChangedPassword) {
        lastPushedState?.copy(textPassword = event.changedTo)?.push()
    }

    private fun toggleButtonsEnabled() {
        lastPushedState?.run {
            copy(buttonsEnabled = !buttonsEnabled)
        }?.push()
    }
}