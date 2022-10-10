package com.tlopez.tello_controller.presentation.controller_screen

import android.graphics.Bitmap
import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState
import com.tlopez.tello_controller.domain.models.TelloState

sealed interface ControllerViewEvent : ViewEvent {
    data class ChangedMovement(
        val roll: Int,
        val pitch: Int,
        val throttle: Int,
        val yaw: Int
    ) : ControllerViewEvent

    object ClickedBreak : ControllerViewEvent
    object ClickedConnect : ControllerViewEvent
    object ClickedLand : ControllerViewEvent
    object ClickedTakeoff : ControllerViewEvent
    object ClickedStartVideo : ControllerViewEvent
}

data class ControllerViewState(
    val telloState: TelloState?,
    val latestFrame: Bitmap?
) : ViewState