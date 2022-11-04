package com.tlopez.authPresentation.verifyEmail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.amazonaws.AmazonClientException
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException
import com.tlopez.authDomain.usecase.ResendVerification
import com.tlopez.authDomain.usecase.SignInUser
import com.tlopez.authDomain.usecase.VerifyUser
import com.tlopez.authPresentation.AuthDestination
import com.tlopez.authPresentation.AuthDestination.*
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
    private val signInUser: SignInUser,
    private val verifyUser: VerifyUser,
    private val resendVerification: ResendVerification,
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<VerifyEmailViewState, VerifyEmailViewEvent, AuthDestination>() {

    private val password: String = savedStateHandle["password"] ?: error("Missing password.")

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
        resetErrorGeneral()
        toggleButtons()
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                if (textCode.isBlank()) {
                    copy(errorMessageCode = "Cannot be blank").push()
                } else {
                    verifyUser(
                        username = username,
                        confirmationCode = textCode
                    )
                        .doOnSuccess {
                            signInUser(username, password)
                                .doOnSuccess {
                                    withContext(Dispatchers.Main) {
                                        routeTo(NavigateFeed)
                                    }
                                }
                                .doOnFailure {
                                    withContext(Dispatchers.Main) {
                                        routeTo(NavigateLogin)
                                    }
                                }

                        }
                        .doOnFailure {
                            lastPushedState?.run {
                                when (it?.cause) {
                                    is CodeMismatchException -> copy(
                                        errorMessageCode = "This is not the correct code"
                                    )
                                    is AmazonClientException -> copy(
                                        errorMessageGeneral = "Unable to connect to the internet"
                                    )
                                    else -> copy(
                                        errorMessageGeneral = "An unknown error (${it?.cause}) occurred."
                                    )
                                }
                            }?.push()
                        }
                }
                toggleButtons()
            }
        }
    }

    private fun onClickedResendCode() {
        resetErrorGeneral()
        viewModelScope.launch(Dispatchers.IO) {
            lastPushedState?.apply {
                resendVerification(username)
                    .doOnSuccess {
                        copy(successMessageResend = "Resent verification email").push()
                    }
                    .doOnFailure {
                        copy(errorMessageResend = "Unable to send verification email").push()
                    }
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

    private fun resetErrorGeneral() {
        lastPushedState?.copy(
            errorMessageGeneral = null,
            errorMessageResend = null,
            successMessageResend = null
        )?.push()
    }

    private fun toggleButtons() {
        lastPushedState?.run { copy(buttonsEnabled = !buttonsEnabled) }?.push()
    }
}