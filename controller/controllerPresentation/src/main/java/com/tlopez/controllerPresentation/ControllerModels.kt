package com.tlopez.controllerPresentation

import android.graphics.Bitmap
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

        val bitmapLatest: Bitmap?
        val flightLengthMs: Int
        val telloState: TelloState?
        val videoOn: Boolean

        fun toConnectedIdle() = ConnectedIdle(bitmapLatest, telloState, videoOn)
        fun toLanded() = Landed(bitmapLatest, flightLengthMs, telloState, videoOn)
        fun toLanding() = Landing(bitmapLatest, flightLengthMs, telloState, videoOn)
        fun toTakingOff() = TakingOff(bitmapLatest, telloState, videoOn)
        fun toFlying() = Flying(bitmapLatest, flightLengthMs, telloState, videoOn)
        fun toggleVideo(): Connected
        fun updateBitmap(bitmap: Bitmap?): Connected
        fun updateTelloState(state: TelloState): Connected

        data class ConnectedIdle(
            override val bitmapLatest: Bitmap? = null,
            override val telloState: TelloState? = null,
            override val videoOn: Boolean = false
        ) : Connected {
            override val flightLengthMs: Int = 0
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateBitmap(bitmap: Bitmap?) = copy(bitmapLatest = bitmap)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }

        data class Landed(
            override val bitmapLatest: Bitmap? = null,
            override val flightLengthMs: Int,
            override val telloState: TelloState?,
            override val videoOn: Boolean
        ) : Connected {
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateBitmap(bitmap: Bitmap?) = copy(bitmapLatest = bitmap)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }

        data class Landing(
            override val bitmapLatest: Bitmap? = null,
            override val flightLengthMs: Int,
            override val telloState: TelloState?,
            override val videoOn: Boolean
        ) : Connected {
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateBitmap(bitmap: Bitmap?) = copy(bitmapLatest = bitmap)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }

        data class TakingOff(
            override val bitmapLatest: Bitmap? = null,
            override val telloState: TelloState?,
            override val videoOn: Boolean
        ) :
            Connected {
            override val flightLengthMs: Int = 0
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateBitmap(bitmap: Bitmap?) = copy(bitmapLatest = bitmap)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }

        data class Flying(
            override val bitmapLatest: Bitmap? = null,
            override val flightLengthMs: Int,
            override val telloState: TelloState?,
            override val videoOn: Boolean
        ) : Connected {
            override fun toggleVideo() = copy(videoOn = !videoOn)
            override fun updateBitmap(bitmap: Bitmap?) = copy(bitmapLatest = bitmap)
            override fun updateTelloState(state: TelloState) = copy(telloState = state)
        }
    }
}