package com.tlopez.tello_controller.presentation.thumbstick

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity

/**
 * @param thumbstickRelativePercentSize The relative size of the thumbstick relative
 * to its container [0f --> 1f]
 * @param thumbstickState Where the thumbstick is currently held relative to its container's center
 * @param onThumbstickDraggedToState Callback when a thumbstick is dragged to a new [ThumbstickState]
 * @param onThumbstickReleased Callback when a thumbstick is released
 */
@Composable
fun Thumbstick(
    modifier: Modifier = Modifier,
    colorContainer: Color = Color.Cyan,
    colorThumbstick: Color = Color.Gray,
    thumbstickRelativePercentSize: Float = 0.8f,
    thumbstickState: ThumbstickState = ThumbstickState(0f, 0f),
    onThumbstickDraggedToState: (ThumbstickState) -> Unit,
    onThumbstickReleased: () -> Unit
) {
    /** Set within Canvas draw block **/
    BoxWithConstraints {
        val radiusContainer = LocalDensity.current.run {
            minOf(maxHeight, maxWidth).roundToPx() / 2.0f
        }
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            onThumbstickDraggedToState(
                                thumbstickState.run {
                                    val draggedX = dragAmount.x / radiusContainer
                                    val draggedY = dragAmount.y / radiusContainer
                                    copy(
                                        fractionHorizontal = fractionHorizontal + draggedX,
                                        fractionVertical = fractionVertical + draggedY
                                    )
                                }
                            )
                            change.consume()
                        },
                        onDragCancel = { onThumbstickReleased() },
                        onDragEnd = { onThumbstickReleased() }
                    )
                }) {
            val radiusThumbstick = radiusContainer * thumbstickRelativePercentSize
            drawCircle(color = colorContainer, radius = radiusContainer)
            drawCircle(
                color = colorThumbstick,
                radius = radiusThumbstick,
                center = thumbstickState.run {
                    center + Offset(
                        x = radiusContainer * fractionHorizontal,
                        y = radiusContainer * fractionVertical,
                    )
                }
            )
        }
    }
}