package com.tlopez.feedPresentation.quadrantGraph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuadrantGraph(
    positionData: PositionData,
    modifier: Modifier
) {
    Canvas(modifier = Modifier.size(250.dp).background(Color.White)) {
        val maximumPosition = 100
        val halfHeight = center.y
        val halfWidth = center.x
        var lastOffset: Offset? = null
        positionData.positionsAsScaledOffsets(size.width, size.height).forEach {
            val x = (it)
            val y = (it)
            val newOffset = it
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
                it
            )
        }
    }
    Text("X Traversal: ${"%.1f".format(positionData.distanceFeetX)} ft")
    Text("Y Traversal: ${"%.1f".format(positionData.distanceFeetY)} ft")
    positionData.percentErrorProjectiles().apply {
        Text("Closest distances (5ft, 0ft), (10ft, 0ft), (15ft, 0ft)...")
        Text("Mean: $mean ft")
        Text("Variance: $variance ft")
        Text("Std. Dev.: $standardDeviation ft")
    }

}