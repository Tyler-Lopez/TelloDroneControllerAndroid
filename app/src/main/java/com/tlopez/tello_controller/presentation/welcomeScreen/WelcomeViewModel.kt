package com.tlopez.tello_controller.presentation.welcomeScreen

import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.presentation.MainDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(
) : BaseRoutingViewModel<WelcomeViewState, WelcomeViewEvent, MainDestination>() {

    init {
        WelcomeViewState.push()
    }

    override fun onEvent(event: WelcomeViewEvent) {
        TODO("Not yet implemented")
    }

}