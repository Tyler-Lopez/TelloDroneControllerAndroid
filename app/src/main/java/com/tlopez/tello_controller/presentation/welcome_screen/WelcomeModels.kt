package com.tlopez.tello_controller.presentation.welcome_screen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface WelcomeViewEvent : ViewEvent {

}

object WelcomeViewState : ViewState