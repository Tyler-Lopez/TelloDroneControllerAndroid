package com.tlopez.tello_controller.presentation.verifyEmailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.usecase.RegisterUserUseCase
import com.tlopez.tello_controller.domain.usecase.SignInUserUseCase
import com.tlopez.tello_controller.domain.usecase.VerifyUserUseCase
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewEvent
import com.tlopez.tello_controller.presentation.verifyEmailScreen.VerifyEmailViewEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val verifyUserUseCase: VerifyUserUseCase,
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<VerifyEmailViewState, VerifyEmailViewEvent, MainDestination>() {

    init {
        VerifyEmailViewState(
            email = savedStateHandle["email"] ?: error("Missing e-mail"),
            username = savedStateHandle["username"] ?: error("Missing username")
        ).push()
    }

    override fun onEvent(event: VerifyEmailViewEvent) {
        when (event) {
            is ClickedVerify -> onClickedVerify()
            is TextChangedCode -> onTextChangedCode(event)
        }
    }

    private fun onClickedVerify() {
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                verifyUserUseCase(
                    username = username,
                    confirmationCode = textCode
                )
            }
        }
    }

    private fun onTextChangedCode(event: TextChangedCode) {
        lastPushedState?.copy(textCode = event.changedTo)?.push()
    }
}