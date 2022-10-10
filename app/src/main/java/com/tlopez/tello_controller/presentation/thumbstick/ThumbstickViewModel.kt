package com.tlopez.tello_controller.presentation.thumbstick

import com.tlopez.tello_controller.architecture.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.tlopez.tello_controller.presentation.thumbstick.ThumbstickViewEvent.*
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class ThumbstickViewModel @Inject constructor() :
    BaseViewModel<ThumbstickViewState, ThumbstickViewEvent>() {

    init {
        pushState(ThumbstickViewState(0f, 0f))
    }

    override fun onEvent(event: ThumbstickViewEvent) {
        when (event) {
            is DraggedThumbstick -> onDraggedThumbstick(event)
            is ReleasedThumbstick -> onReleasedThumbstick(event)
        }
    }

    private fun onDraggedThumbstick(event: DraggedThumbstick) {
        lastPushedState?.run {
            val range = (event.thumbstickRadius * -1f)..event.thumbstickRadius
            val xPercent = (xOffsetFraction + event.draggedTo.x).coerceIn(range) / event.thumbstickRadius
            val yPercent = (yOffsetFraction + event.draggedTo.y).coerceIn(range) / event.thumbstickRadius
            event.onThumbstickDraggedToPercent(Pair(xPercent, yPercent))
            copy(
                xOffsetFraction = (xOffsetFraction + event.draggedTo.x).coerceIn(range),
                yOffsetFraction = (yOffsetFraction + event.draggedTo.y).coerceIn(range),
            )
        }?.push()
    }

    private fun onReleasedThumbstick(event: ThumbstickViewEvent.ReleasedThumbstick) {
        lastPushedState?.run {
            event.onThumbstickDraggedToPercent(Pair(0f, 0f))
            copy(
                xOffsetFraction = 0f,
                yOffsetFraction = 0f,
            )
        }?.push()
    }
}