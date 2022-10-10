package com.tlopez.tello_controller.presentation.thumbstick

import androidx.compose.ui.geometry.Offset
import com.tlopez.tello_controller.architecture.ViewEvent
import com.tlopez.tello_controller.architecture.ViewState

sealed interface ThumbstickViewEvent : ViewEvent {
    data class DraggedThumbstick(
        val draggedTo: Offset,
        val thumbstickRadius: Float,
        val onThumbstickDraggedToPercent: ((Pair<Float, Float>) -> Unit)
    ) : ThumbstickViewEvent

    data class ReleasedThumbstick(
        val onThumbstickDraggedToPercent: ((Pair<Float, Float>) -> Unit)
    ) : ThumbstickViewEvent
}

data class ThumbstickViewState(
    val xOffsetFraction: Float,
    val yOffsetFraction: Float
) : ViewState