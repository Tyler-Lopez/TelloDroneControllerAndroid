package com.tlopez.tello_controller.presentation.thumbstick

import android.util.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerId
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Thumbstick(
    modifier: Modifier,
    thumbstickRelativePercentSize: Float = 0.8f,
    viewModel: ThumbstickViewModel = hiltViewModel()
) {
    viewModel.viewState.collectAsState().value?.apply {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            println("on drag")
                            viewModel.onEvent(
                                ThumbstickViewEvent.DraggedThumbstick(
                                    dragAmount,
                                    size.run {
                                        minOf(width, height) / 2f
                                    }
                                )
                            )
                            change.consume()
                        },
                        onDragEnd = {
                            println("on drag end ")
                            viewModel.onEvent(
                                ThumbstickViewEvent.ReleasedThumbstick
                            )
                        },
                        onDragStart = {
                            println("on drag start")
                        })
                }
        ) {
            val radius = size.minDimension / 2.0f
            drawCircle(
                color = Color.Cyan,
                radius = radius,
                center = center
            )
            val thumbstickCenter = Offset(
                center.x + (xOffsetFraction * 0.5f),
                center.y + (yOffsetFraction * 0.5f)
            )
            drawCircle(
                color = Color.Black,
                radius = (radius * thumbstickRelativePercentSize).coerceIn(0f, radius),
                center = thumbstickCenter
            )

        }
    }
}