package com.tlopez.controllerPresentation

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface ControllerViewEvent : ViewEvent {

}

sealed interface ControllerViewState : ViewState {
    object Connecting : ControllerViewState
    object TakingOff : ControllerViewState
}