package com.tlopez.controllerPresentation

import com.tlopez.controllerDomain.TelloState
import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface ControllerViewEvent : ViewEvent {

}

sealed interface ControllerViewState : ViewState {
    sealed interface Disconnected : ControllerViewState {
        object DisconnectedError : Disconnected
        object DisconnectedIdle : Disconnected
    }

    sealed interface Connected : ControllerViewState {
        val telloState: TelloState?

        data class ConnectedIdle(override val telloState: TelloState? = null) : Connected
        data class TakingOff(override val telloState: TelloState? = null) : Connected
        data class Flying(override val telloState: TelloState? = null) : Connected
    }
}