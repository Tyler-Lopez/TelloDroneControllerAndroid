package com.tlopez.tello_controller.presentation.registerScreen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.usecase.RegisterUserUseCase
import com.tlopez.tello_controller.domain.usecase.SignInUserUseCase
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.MainDestination.*
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewEvent.*
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewState.*
import com.tlopez.tello_controller.util.doOnFailure
import com.tlopez.tello_controller.util.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                    username = textUsername,
                    password = textPassword
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