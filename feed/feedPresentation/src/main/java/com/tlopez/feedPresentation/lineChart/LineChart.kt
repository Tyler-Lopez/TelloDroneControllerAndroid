package com.tlopez.feedPresentation.lineChart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun LineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints {
        val maxCursorPosition: Float = LocalDensity.current.run { maxWidth.toPx() }
        var cursorPosition by remember { mutableStateOf(maxCursorPosition) }
        println("Width height is ${this.maxWidth} $maxHeight")
        Canvas(
            modifier = modifier
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, amount ->
                            cursorPosition = change.position.x
                            change.consume()
                        },
                        onDragEnd = {
                            val nearestPoint = lineChartData.nearestNormalizedPoint(cursorPosition / maxCursorPosition)
                            println("Nearest point was $nearestPoint")
                            cursorPosition = maxCursorPosition * nearestPoint
                        }
                    )
                }
        ) {
            println("YO, cursor position is ${cursorPosition}")
            println("Max width from canvas is ${size.width}")
            lineChartData.apply {
                val dataSetColor = Color.Black
                var prevPoint: Offset? = null

                dataSetNormalized.forEach {
                    //    println("Trying to draw normalized... $it")
                    val newPoint = Offset(
                        size.width * it.second,
                        size.height * if (it.first.isNaN()) (size.height / 2f) else it.first
                    )
                    prevPoint?.let {
                        drawLine(
                            dataSetColor,
                            it,
                            newPoint,3f

                        )
                    }
                    drawCircle(
                        dataSetColor,
                        2f,
                        newPoint
                    )
                    prevPoint = newPoint
                }
            }
            val lineWidth = 5f
            drawRect(
                Color.Black,
                Offset(cursorPosition - (lineWidth / 2f), 0f),
                Size(lineWidth, size.height),
            )
        }
    }
}