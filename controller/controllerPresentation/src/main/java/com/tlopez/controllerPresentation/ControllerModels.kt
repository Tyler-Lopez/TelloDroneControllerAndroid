package com.tlopez.controllerPresentation

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface ControllerViewEvent : ViewEvent {

}

sealed interface ControllerViewState : ViewState {
    object ConnectedIdle : ControllerViewState
    object DisconnectedError : ControllerViewState
    object DisconnectedIdle : ControllerViewState
    sealed interface InFlight {
        object TakingOff : InFlight
        object Flying : InFlight
    }
}