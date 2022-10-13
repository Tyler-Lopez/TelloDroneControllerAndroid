package com.tlopez.tello_controller.presentation.controllerScreen

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState
import com.tlopez.tello_controller.domain.models.TelloState
import com.tlopez.tello_controller.presentation.thumbstick.ThumbstickState

sealed interface ControllerViewEvent : ViewEvent {
    object ClickedConnect : ControllerViewEvent
    object ClickedLand : ControllerViewEvent
    object ClickedTakeoff : ControllerViewEvent
    data class MovedRollPitchThumbstick(val movedByPercent: Offset) : ControllerViewEvent
    data class MovedThrottleYawThumbstick(val movedByPercent: Offset) : ControllerViewEvent
    object ResetRollPitchThumbstick : ControllerViewEvent
    object ResetThrottleYawThumbstick : ControllerViewEvent
    object ToggleVideo : ControllerViewEvent
}

sealed interface ControllerViewState : ViewState {

    object DisconnectedError : ControllerViewState
    object DisconnectedIdle : ControllerViewState

    sealed interface ConnectedViewState : ControllerViewState {

        val latestFrame: Bitmap?
        val videoOn: Boolean

        fun copyWithVideoChange(
            latestFrame: Bitmap? = null,
            videoOn: Boolean? = null
        ) : ConnectedViewState

        data class ConnectedError(
            override val latestFrame: Bitmap? = null,
            override val videoOn: Boolean = false
        ) : ConnectedViewState {
            override fun copyWithVideoChange(latestFrame: Bitmap?, videoOn: Boolean?): ConnectedError {
                return copy(
                    latestFrame = latestFrame ?: this.latestFrame,
                    videoOn = videoOn ?: this.videoOn
                )
            }
        }

        data class ConnectedIdle(
            override val latestFrame: Bitmap? = null,
            override val videoOn: Boolean = false,
            val lastFlightMs: Long? = null
        ) : ConnectedViewState {
            override fun copyWithVideoChange(latestFrame: Bitmap?, videoOn: Boolean?): ConnectedIdle {
                return copy(
                    latestFrame = latestFrame ?: this.latestFrame,
                    videoOn = videoOn ?: this.videoOn
                )
            }
        }

        data class TakingOff(
            override val latestFrame: Bitmap?,
            override val videoOn: Boolean
        ) : ConnectedViewState {
            override fun copyWithVideoChange(latestFrame: Bitmap?, videoOn: Boolean?): TakingOff {
                return copy(
                    latestFrame = latestFrame ?: this.latestFrame,
                    videoOn = videoOn ?: this.videoOn
                )
            }
        }

        data class Flying(
            override val latestFrame: Bitmap?,
            override val videoOn: Boolean,
            val flightLengthMs: Long = 0L,
            val telloState: TelloState? = null,
            val throttleYawThumbstickState: ThumbstickState = ThumbstickState(),
            val rollPitchThumbstickState: ThumbstickState = ThumbstickState()
        ) : ConnectedViewState {
            override fun copyWithVideoChange(latestFrame: Bitmap?, videoOn: Boolean?): Flying {
                return copy(
                    latestFrame = latestFrame ?: this.latestFrame,
                    videoOn = videoOn ?: this.videoOn
                )
            }
        }
    }
}
