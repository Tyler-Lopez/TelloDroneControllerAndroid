package com.tlopez.tello_controller.presentation.loginScreen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.repository.AuthRepository
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
    private val authRepository: AuthRepository,
) : BaseRoutingViewModel<LoginViewState, LoginViewEvent, MainDestination>() {

    init {
        LoginViewState().push()
    }

    override fun onEvent(event: LoginViewEvent) {
        when (event) {
            is ClickedRegister -> onClickedRegister()
        }
    }

    private fun onClickedRegister() {
        routeTo(NavigateRegister)
    }
}