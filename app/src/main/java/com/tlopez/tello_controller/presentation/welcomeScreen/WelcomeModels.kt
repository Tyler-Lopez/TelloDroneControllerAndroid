package com.tlopez.tello_controller.presentation.welcomeScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface WelcomeViewEvent : ViewEvent {
    object ClickedLogout : WelcomeViewEvent
}

object WelcomeViewState : ViewState