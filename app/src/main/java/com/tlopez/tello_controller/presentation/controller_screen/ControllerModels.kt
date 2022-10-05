package com.tlopez.tello_controller.presentation.controller_screen

import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface ControllerViewEvent : ViewEvent {
    object ClickedConnect : ControllerViewEvent
    object ClickedLand : ControllerViewEvent
    object ClickedTakeoff : ControllerViewEvent
}

sealed interface ControllerViewState : ViewState {

}