package com.tlopez.tello_controller.presentation.controller_screen

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState
import com.tlopez.tello_controller.domain.models.TelloState
import com.tlopez.tello_controller.presentation.thumbstick.ThumbstickState

sealed interface ControllerViewEvent : ViewEvent {
    object ClickedBreak : ControllerViewEvent
    object ClickedConnect : ControllerViewEvent
    object ClickedLand : ControllerViewEvent
    object ClickedTakeoff : ControllerViewEvent
    object ClickedStartVideo : ControllerViewEvent
    data class MovedRollPitchThumbstick(val movedByPercent: Offset) : ControllerViewEvent
    data class MovedThrottleYawThumbstick(val movedByPercent: Offset) : ControllerViewEvent
    object ResetRollPitchThumbstick : ControllerViewEvent
    object ResetThrottleYawThumbstick : ControllerViewEvent
}

data class ControllerViewState(
    val telloState: TelloState?,
    val thumbstickLeft: ThumbstickState,
    val thumbstickRight: ThumbstickState,
    val latestFrame: Bitmap?
) : ViewState