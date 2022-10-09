package com.tlopez.tello_controller.presentation.thumbstick

import com.tlopez.tello_controller.architecture.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThumbstickViewModel @Inject constructor() :
    BaseViewModel<ThumbstickViewState, ThumbstickViewEvent>() {

    init {
        pushState(ThumbstickViewState(0f, 0f))
    }

    override fun onEvent(event: ThumbstickViewEvent) {
        when (event) {
            is ThumbstickViewEvent.DraggedThumbstick -> onDraggedThumbstick(event)
            is ThumbstickViewEvent.ReleasedThumbstick -> onReleasedThumbstick()
        }
    }

    private fun onDraggedThumbstick(event: ThumbstickViewEvent.DraggedThumbstick) {
        lastPushedState?.run {
            println("here dragged to ${event.draggedTo}")
            val range = (event.thumbstickRadius * -1f)..event.thumbstickRadius
            copy(
                xOffsetFraction = (xOffsetFraction + event.draggedTo.x).coerceIn(range),
                yOffsetFraction = (yOffsetFraction + event.draggedTo.y).coerceIn(range),
            )
        }?.push()
    }

    private fun onReleasedThumbstick() {
        lastPushedState?.run {
            copy(
                xOffsetFraction = 0f,
                yOffsetFraction = 0f,
            )
        }?.push()
    }
}