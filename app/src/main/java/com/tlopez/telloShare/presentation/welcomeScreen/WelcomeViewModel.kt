package com.tlopez.telloShare.presentation.welcomeScreen

import androidx.lifecycle.viewModelScope
import com.tlopez.telloShare.architecture.BaseRoutingViewModel
import com.tlopez.telloShare.domain.usecase.LogoutUserUseCase
import com.tlopez.telloShare.presentation.MainDestination
import com.tlopez.telloShare.presentation.MainDestination.*
import com.tlopez.telloShare.presentation.welcomeScreen.WelcomeViewEvent.*
import com.tlopez.telloShare.util.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                .doOnSuccess {
                    withContext(Dispatchers.Main) {
                        routeTo(NavigateLogin)
                    }
                }
        }
    }

}