package com.tlopez.tello_controller.presentation.controller_screen

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

        data class ConnectedError(
            override val latestFrame: Bitmap? = null,
            override val videoOn: Boolean = false
        ) : ConnectedViewState

        data class ConnectedIdle(
            override val latestFrame: Bitmap? = null,
            override val videoOn: Boolean = false,
            val lastFlightMs: Long? = null
        ) : ConnectedViewState

        data class TakingOff(
            override val latestFrame: Bitmap?,
            override val videoOn: Boolean
        ) : ConnectedViewState

        data class Flying(
            override val latestFrame: Bitmap?,
            override val videoOn: Boolean,
            val flightLengthMs: Long = 0L,
            val telloState: TelloState? = null,
            val throttleYawThumbstickState: ThumbstickState = ThumbstickState(),
            val rollPitchThumbstickState: ThumbstickState = ThumbstickState()
        ) : ConnectedViewState
    }
}
