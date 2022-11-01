package com.tlopez.authPresentation.verifyEmail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException
import com.tlopez.authDomain.usecase.ResendVerification
import com.tlopez.authDomain.usecase.VerifyUser
import com.tlopez.authPresentation.AuthDestination
import com.tlopez.authPresentation.AuthDestination.NavigateFeed
import com.tlopez.authPresentation.verifyEmail.VerifyEmailViewEvent.*
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val verifyUser: VerifyUser,
    private val resendVerification: ResendVerification,
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<VerifyEmailViewState, VerifyEmailViewEvent, AuthDestination>() {

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
                verifyUser(
                    username = username,
                    confirmationCode = textCode
                )
                    .doOnSuccess {
                        withContext(Dispatchers.Main) {
                            routeTo(NavigateFeed)
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
                resendVerification(username)
                    .doOnSuccess { }
                    .doOnFailure { }
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