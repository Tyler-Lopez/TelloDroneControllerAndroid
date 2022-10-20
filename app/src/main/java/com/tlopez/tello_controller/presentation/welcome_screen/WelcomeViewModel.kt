package com.tlopez.tello_controller.presentation.welcome_screen

import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.controllerScreen.ControllerViewEvent
import com.tlopez.tello_controller.presentation.controllerScreen.ControllerViewState
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