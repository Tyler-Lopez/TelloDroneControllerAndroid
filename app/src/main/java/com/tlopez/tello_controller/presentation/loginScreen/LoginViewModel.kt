package com.tlopez.tello_controller.presentation.loginScreen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.repository.AuthRepository
import com.tlopez.tello_controller.domain.usecase.SignInUserUseCase
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.MainDestination.*
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewEvent.*
import com.tlopez.tello_controller.presentation.welcomeScreen.WelcomeViewEvent
import com.tlopez.tello_controller.presentation.welcomeScreen.WelcomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase
) : BaseRoutingViewModel<LoginViewState, LoginViewEvent, MainDestination>() {

    init {
        LoginViewState().push()
    }

    override fun onEvent(event: LoginViewEvent) {
        when (event) {
            is ClickedLogin -> onClickedLogin()
            is ClickedRegister -> onClickedRegister()
            is TextChangedPassword -> onTextChangedPassword(event)
            is TextChangedUsername -> onTextChangedUsername(event)
            is ToggledPassVisibility -> onToggledPassVisibility()
        }
    }

    private fun onClickedLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                signInUserUseCase(textUsername, textPassword)
            }
        }
    }

    private fun onClickedRegister() {
        routeTo(NavigateRegister)
    }

    private fun onTextChangedPassword(event: TextChangedPassword) {
        lastPushedState?.copy(textPassword = event.changedTo)?.push()
    }

    private fun onTextChangedUsername(event: TextChangedUsername) {
        lastPushedState?.copy(textUsername = event.changedTo)?.push()
    }

    private fun onToggledPassVisibility() {
        lastPushedState?.run {
            copy(passHidden = !passHidden)
        }?.push()
    }
}