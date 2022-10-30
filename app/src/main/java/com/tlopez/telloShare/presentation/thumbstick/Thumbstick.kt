package com.tlopez.telloShare.presentation.thumbstick

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity

/**
 * Composable used to represent a thumbstick which a user may drag and release.
 *
 * @param thumbstickRelativePercentSize The relative size of the thumbstick relative
 * to its container [0f --> 1f]
 * @param thumbstickState Where the thumbstick is currently held relative to its container's center
 * @param onThumbstickDraggedToState Callback when a thumbstick is dragged to a new [ThumbstickState]
 * @param onThumbstickReleased Callback when a thumbstick is released
 */
@Composable
fun Thumbstick(
    thumbstickState: ThumbstickState,
    modifier: Modifier = Modifier,
    colorContainerExterior: Color = Color.Gray,
    colorContainerInterior: Color = Color.DarkGray,
    colorThumbstick: Color = Color.LightGray,
    onThumbstickDraggedToFloatPercent: (Offset) -> Unit,
    onThumbstickReleased: () -> Unit
) {
    /** Set within Canvas draw block **/
    BoxWithConstraints(modifier = modifier) {
        val radiusView = LocalDensity.current.run {
            (minOf(maxHeight, maxWidth).roundToPx() / 2.0f)
        }
        val radiusContainerExterior = radiusView
        val radiusContainerInterior = radiusContainerExterior * 0.5f
        val radiusThumbstick = radiusContainerInterior * 0.7f
        Canvas(
            modifier = Modifier
                .requiredSize(minWidth, minWidth)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            val draggedX = dragAmount.x / radiusView
                            val draggedY = dragAmount.y / radiusView
                            onThumbstickDraggedToFloatPercent(Offset(draggedX, draggedY))
                            change.consume()
                        },
                        onDragCancel = { onThumbstickReleased() },
                        onDragEnd = { onThumbstickReleased() }
                    )
                }) {
            drawCircle(color = colorContainerExterior, radius = radiusContainerExterior)
            drawCircle(color = colorContainerInterior, radius = radiusContainerInterior)
            drawCircle(
                color = colorThumbstick,
                radius = radiusThumbstick,
                center = thumbstickState.run {
                    center + Offset(
                        x = radiusContainerInterior * fractionHorizontal,
                        y = radiusContainerInterior * fractionVertical,
                    )
                }
            )
        }
    }
}