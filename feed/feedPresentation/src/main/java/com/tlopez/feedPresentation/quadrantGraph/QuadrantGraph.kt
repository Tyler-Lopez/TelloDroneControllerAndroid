package com.tlopez.feedPresentation.quadrantGraph

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun QuadrantGraph(
    positions: List<Position>,
    modifier: Modifier
) {
    Canvas(modifier = modifier) {
        drawLine(
            Color.Gray,
            Offset(0f, size.height / 2f),
            Offset(size.width, size.height / 2f)
        )
        drawLine(
            Color.Gray,
            Offset(size.width / 2f, 0f),
            Offset(size.width / 2f, size.height)
        )
        val maximumPosition = 100
        val halfHeight = center.y
        val halfWidth = center.x
        var lastOffset: Offset? = null
        positions.forEach {
            println("Position is $it")
            val x = (it.positionDecimeterX / maximumPosition) * halfWidth + halfWidth
            val y = (it.positionDecimeterY / maximumPosition) * halfHeight + halfHeight
            val newOffset = Offset(x, y)
            lastOffset?.let {
                drawLine(
                    Color.Black,
                    it,
                    newOffset,
                    2f
                )
            }
            lastOffset = newOffset
            drawCircle(
                Color.Red,
                1f,
                Offset(x, y)
            )
        }
    }

}