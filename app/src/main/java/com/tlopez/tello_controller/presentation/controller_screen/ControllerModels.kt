package com.tlopez.tello_controller.presentation.controller_screen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState
import com.tlopez.tello_controller.domain.models.TelloState

sealed interface ControllerViewEvent : ViewEvent {
    object ClickedConnect : ControllerViewEvent
    object ClickedForward : ControllerViewEvent
    object ClickedLand : ControllerViewEvent
    object ClickedSetSpeed : ControllerViewEvent
    object ClickedTakeoff : ControllerViewEvent
}

data class ControllerViewState(
    val telloState: TelloState?
) : ViewState