package com.tlopez.tello_controller.presentation.registerScreen

import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.repository.AuthRepository
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewEvent.*
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
) : BaseRoutingViewModel<RegisterViewState, RegisterViewEvent, MainDestination>() {

    init {
        RegisterViewState().push()
    }

    override fun onEvent(event: RegisterViewEvent) {
        when (event) {
            is TextChangedUsername -> onTextChangedUsername(event)
            is TextChangedPassword -> onTextChangedPassword(event)
        }
    }

    private fun onTextChangedUsername(event: TextChangedUsername) {
        lastPushedState?.copy(textUsername = event.changedTo)?.push()
    }

    private fun onTextChangedPassword(event: TextChangedPassword) {
        lastPushedState?.copy(textPassword = event.changedTo)?.push()
    }
}