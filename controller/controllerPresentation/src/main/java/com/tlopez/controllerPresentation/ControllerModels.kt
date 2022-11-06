package com.tlopez.controllerPresentation

import com.tlopez.controllerDomain.TelloState
import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface ControllerViewEvent : ViewEvent {
    object ClickedLand : ControllerViewEvent
    object ClickedTakeOff : ControllerViewEvent
    object ToggledVideo : ControllerViewEvent
}

sealed interface ControllerViewState : ViewState {
    sealed interface Disconnected : ControllerViewState {
        object DisconnectedError : Disconnected
        object DisconnectedIdle : Disconnected
    }

    sealed interface Connected : ControllerViewState {
        val telloState: TelloState?
        val videoOn: Boolean
        fun toConnectedIdle() = ConnectedIdle(telloState, videoOn)
        fun toLanding() = Landing(telloState, videoOn)
        fun toTakingOff() = TakingOff(telloState, videoOn)
        fun toFlying() = Flying(telloState, videoOn)
        fun toggleVideo(): Connected
        fun updateTelloState(state: TelloState): Connected

        data class ConnectedIdle(
            override val telloState: TelloState? = null,
            override val videoOn: Boolean = false
        ) : Connected {
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }

        data class Landing(
            override val telloState: TelloState? = null,
            override val videoOn: Boolean
        ) : Connected {
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }

        data class TakingOff(
            override val telloState: TelloState? = null,
            override val videoOn: Boolean
        ) : Connected {
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }

        data class Flying(
            override val telloState: TelloState? = null,
            override val videoOn: Boolean
        ) : Connected {
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }
    }
}