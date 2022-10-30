package com.tlopez.telloShare.presentation.welcomeScreen

import com.tlopez.telloShare.architecture.ViewEvent
import com.tlopez.telloShare.architecture.ViewState

sealed interface WelcomeViewEvent : ViewEvent {
    object ClickedLogout : WelcomeViewEvent
}

object WelcomeViewState : ViewState