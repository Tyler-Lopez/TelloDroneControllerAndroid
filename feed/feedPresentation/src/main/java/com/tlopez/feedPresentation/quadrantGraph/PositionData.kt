package com.tlopez.feedPresentation.quadrantGraph

import androidx.compose.ui.geometry.Offset
import java.util.concurrent.TimeUnit

data class Velocity(
    val velocityDecimeterSecondsX: Int,
    val velocityDecimeterSecondsY: Int,
    val timeMilliseconds: Int
)

data class Position(
    val positionDecimeterX: Float,
    val positionDecimeterY: Float
)

data class PositionData(
    val velocities: List<Velocity>
) {
    val positions: List<Position> = velocities.run {
        var lastPosition = Position(0f, 0f)
        var lastVelocity = Velocity(0, 0, 0)
        map {
            val secondsSinceLastVelocity: Float =
                (it.timeMilliseconds - lastVelocity.timeMilliseconds) / 1000f

            val displacementX = lastVelocity.velocityDecimeterSecondsX * secondsSinceLastVelocity
            val displacementY = lastVelocity.velocityDecimeterSecondsY * secondsSinceLastVelocity

            val newPosition = Position(
                lastPosition.positionDecimeterX + displacementX,
                lastPosition.positionDecimeterY + displacementY
            )

            lastPosition = newPosition
            lastVelocity = it
            newPosition
        }
    }

    private val maxX = positions.maxOf { it.positionDecimeterX }
    private val maxY = positions.maxOf { it.positionDecimeterY }
    private val minX = positions.minOf { it.positionDecimeterX }
    private val minY = positions.minOf { it.positionDecimeterY }
    val rangeX = maxX - minX
    val rangeY = maxY - minY
    val centerPosition = Position(
        positionDecimeterX = (minX + maxX) / 2f,
        positionDecimeterY = (minY + maxY) / 2f
    )

}

