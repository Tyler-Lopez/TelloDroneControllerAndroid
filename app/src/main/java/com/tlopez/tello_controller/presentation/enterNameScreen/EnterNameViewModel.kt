package com.tlopez.tello_controller.presentation.enterNameScreen

import com.tlopez.tello_controller.architecture.BaseRoutingViewModel
import com.tlopez.tello_controller.presentation.MainDestination
import com.tlopez.tello_controller.presentation.enterNameScreen.EnterNameViewEvent.*
import com.tlopez.tello_controller.presentation.welcomeScreen.WelcomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterNameViewModel @Inject constructor(
) : BaseRoutingViewModel<EnterNameViewState, EnterNameViewEvent, MainDestination>() {

    init {
        EnterNameViewState().push()
    }

    override fun onEvent(event: EnterNameViewEvent) {
        when (event) {
            is TextChangedName -> onTextChangedName(event)
        }
    }

    private fun onTextChangedName(event: TextChangedName) {
        lastPushedState?.copy(textName = event.changedTo)?.push()
    }
}