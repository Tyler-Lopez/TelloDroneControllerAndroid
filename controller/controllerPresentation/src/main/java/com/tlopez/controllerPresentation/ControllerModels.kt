package com.tlopez.controllerPresentation

import com.tlopez.controllerDomain.TelloState
import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface ControllerViewEvent : ViewEvent {
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

        fun copyConnected(
            telloState: TelloState? = null,
            videoOn: Boolean? = null
        ): Connected

        data class ConnectedIdle(
            override val telloState: TelloState? = null,
            override val videoOn: Boolean = false
        ) : Connected {
            override fun copyConnected(
                telloState: TelloState?,
                videoOn: Boolean?
            ): ConnectedIdle {
                return copy(
                    telloState = telloState ?: this.telloState,
                    videoOn = videoOn ?: this.videoOn
                )
            }
        }

        data class TakingOff(
            override val telloState: TelloState? = null,
            override val videoOn: Boolean
        ) : Connected {
            override fun copyConnected(
                telloState: TelloState?,
                videoOn: Boolean?
            ): TakingOff {
                return copy(
                    telloState = telloState ?: this.telloState,
                    videoOn = videoOn ?: this.videoOn
                )
            }
        }

        data class Flying(
            override val telloState: TelloState? = null,
            override val videoOn: Boolean
        ) : Connected {
            override fun copyConnected(
                telloState: TelloState?,
                videoOn: Boolean?
            ): Flying {
                return copy(
                    telloState = telloState ?: this.telloState,
                    videoOn = videoOn ?: this.videoOn
                )
            }
        }
    }
}