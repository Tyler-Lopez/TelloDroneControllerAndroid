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

    /**
     * Provided a list of positions, return a new position which is centered in the theoretical
     * square created between the bottom-left and the top-right.
     */
    fun List<Position>.center(): Position {
        val minX = minOf { it.positionDecimeterX }
        val maxX = maxOf { it.positionDecimeterX }
        val minY = minOf { it.positionDecimeterY }
        val maxY = maxOf { it.positionDecimeterY}
        return Position(
            positionDecimeterX = (minX + maxX) / 2f,
            positionDecimeterY = (minY + maxY) / 2f
        )
    }
}

