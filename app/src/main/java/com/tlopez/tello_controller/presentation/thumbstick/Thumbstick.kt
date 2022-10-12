package com.tlopez.tello_controller.presentation.thumbstick

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    colorContainer: Color = Color.Cyan,
    colorThumbstick: Color = Color.Gray,
    thumbstickRelativePercentSize: Float = 0.8f,
    onThumbstickDraggedToFloatPercent: (Offset) -> Unit,
    onThumbstickReleased: () -> Unit
) {
    /** Set within Canvas draw block **/
    BoxWithConstraints(modifier = modifier.background(Color.Red)) {
        val radiusContainer = LocalDensity.current.run {
            minOf(maxHeight, maxWidth).roundToPx() / 2.0f
        }
        println("hey making canvas state is $thumbstickState")
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            val draggedX = dragAmount.x / radiusContainer
                            val draggedY = dragAmount.y / radiusContainer
                            //    println("fraction horizontal was $fractionHorizontal")
                            //   println("drag amount was ${dragAmount.x}")
                            //  println("drag x was ${draggedX}")
                            println("but in here thumbstick state is $thumbstickState")
                            onThumbstickDraggedToFloatPercent(Offset(draggedX, draggedY))
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