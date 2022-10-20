package com.tlopez.tello_controller.presentation.enterNameScreen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface EnterNameViewEvent : ViewEvent {
    data class TextChangedName(val changedTo: String) : EnterNameViewEvent
}

data class EnterNameViewState(
    val textName: String = String()
) : ViewState