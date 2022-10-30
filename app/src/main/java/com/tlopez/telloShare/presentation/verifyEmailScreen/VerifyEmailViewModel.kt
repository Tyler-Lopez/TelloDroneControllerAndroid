package com.tlopez.telloShare.presentation.verifyEmailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException.*
import com.tlopez.telloShare.architecture.BaseRoutingViewModel
import com.tlopez.telloShare.domain.usecase.ResendVerificationUseCase
import com.tlopez.telloShare.domain.usecase.VerifyUserUseCase
import com.tlopez.telloShare.presentation.MainDestination
import com.tlopez.telloShare.presentation.MainDestination.*
import com.tlopez.telloShare.presentation.verifyEmailScreen.VerifyEmailViewEvent.*
import com.tlopez.telloShare.util.doOnFailure
import com.tlopez.telloShare.util.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val verifyUserUseCase: VerifyUserUseCase,
    private val resendVerificationUseCase: ResendVerificationUseCase,
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<VerifyEmailViewState, VerifyEmailViewEvent, MainDestination>() {

    init {
        VerifyEmailViewState(
            email = savedStateHandle["email"],
            username = savedStateHandle["username"] ?: error("Missing username")
        ).push()
    }

    override fun onEvent(event: VerifyEmailViewEvent) {
        when (event) {
            is ClickedVerify -> onClickedVerify()
            is ClickedResendCode -> onClickedResendCode()
            is ClosedKeyboard -> onClosedKeyboard()
            is TextChangedCode -> onTextChangedCode(event)
        }
    }

    private fun onClickedVerify() {
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                if (textCode.isBlank()) {
                    copy(errorMessageCode = "Cannot be blank").push()
                    return@launch
                }
                copy(buttonsEnabled = false).push()
                verifyUserUseCase(
                    username = username,
                    confirmationCode = textCode
                )
                    .doOnSuccess {
                        withContext(Dispatchers.Main) {
                            routeTo(NavigateWelcome)
                        }
                    }
                    .doOnFailure {
                        when (it) {
                            is CodeMismatchException -> {
                                lastPushedState
                                    ?.copy(errorMessageCode = "This is not the correct code")
                                    ?.push()
                            }
                        }
                    }
            }
            lastPushedState?.copy(buttonsEnabled = true)?.push()
        }
    }

    private fun onClickedResendCode() {
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                resendVerificationUseCase(username)
                    .doOnSuccess {  }
                    .doOnFailure {  }
            }
        }
    }

    private fun onClosedKeyboard() {
        lastPushedState?.apply {
            if (textCode.isBlank()) {
                copy(errorMessageCode = "Cannot be empty").push()
            }
        }
    }

    private fun onTextChangedCode(event: TextChangedCode) {
        lastPushedState?.copy(
            errorMessageCode = null,
            textCode = event.changedTo
        )?.push()
    }
}