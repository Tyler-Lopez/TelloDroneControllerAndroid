package com.tlopez.tello_controller.presentation.welcomeScreen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.domain.usecase.LogoutUserUseCase
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.welcomeScreen.WelcomeViewEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val logoutUserUseCase: LogoutUserUseCase
) : BaseRoutingViewModel<WelcomeViewState, WelcomeViewEvent, MainDestination>() {

    init {
        WelcomeViewState.push()
    }

    override fun onEvent(event: WelcomeViewEvent) {
        when (event) {
            ClickedLogout -> onClickedLogout()
        }
    }

    private fun onClickedLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUserUseCase()
        }
    }

}