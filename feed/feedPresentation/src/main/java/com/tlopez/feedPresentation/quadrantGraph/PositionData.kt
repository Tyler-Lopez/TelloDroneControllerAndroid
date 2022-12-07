package com.tlopez.feedPresentation.quadrantGraph

import androidx.compose.ui.geometry.Offset
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

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
    private val rangeX = maxX - minX
    private val rangeY = maxY - minY
    val distanceFeetX = rangeX / 3.048f
    val distanceFeetY = rangeY / 3.048f

    private val proj1 = 15.24f
    private val proj2 = 30.48f
    private val proj3 = 45.72f

    data class TempMath(
        val mean: Float,
        val variance: Float,
        val standardDeviation: Float
    )

    val closestToFirst = positions.minBy { abs(15.24f - it.positionDecimeterX) }
    val closestToSecond = positions.minBy { abs(30.48f - it.positionDecimeterX) }
    val closestToThird = positions.minBy { abs(45.72f - it.positionDecimeterX) }

    fun percentErrorProjectiles(): TempMath {

        val distance1 = sqrt(
            (closestToFirst.positionDecimeterX - proj1).pow(2) +
                    (closestToFirst.positionDecimeterY - 0).pow(2)
        ) / 3.048f
        val distance2 = sqrt(
            (closestToSecond.positionDecimeterX - proj2).pow(2) +
                    (closestToSecond.positionDecimeterY - 0).pow(2)
        ) / 3.048f
        val distance3 = sqrt(
            (closestToThird.positionDecimeterX - proj3).pow(2) +
                    (closestToThird.positionDecimeterY - 0).pow(2)
        ) / 3.048f
        println("distance1: $distance1, 2 $distance2, 3 $distance3")
        val distances = listOf(
            distance1,
            distance2,
            distance3
        )
        val mean = distances.sum() / 3f
        println("distance mean is $mean")
        val subtractMeanSquaredDistances = distances.map {
            (it - mean).pow(2)
        }
        val sumOfSquares = subtractMeanSquaredDistances.sum()
        val nMinusOne = 2
        val variance = sumOfSquares / nMinusOne
        val standardDeviation = sqrt(variance)
        return TempMath(mean, variance, standardDeviation)
    }

    fun positionsAsScaledOffsets(reqWidthPx: Float, reqHeightPx: Float): List<Offset> {

        val widthScalingFactor = reqWidthPx / rangeX
        val heightScalingFactor = reqHeightPx / rangeY

        val heightWhenWidthScaled = rangeY * widthScalingFactor

        val scalingFactor = if (heightWhenWidthScaled > reqHeightPx) {
            heightScalingFactor

        } else {
            widthScalingFactor
        }

        val centerAdjustmentX = minX + (rangeX / 2f)
        val centerAdjustmentY = minY + (rangeY / 2f)

        val targetCenterX = reqWidthPx / 2f
        val targetCenterY = reqHeightPx / 2f

        val scaledCenterX = centerAdjustmentX * scalingFactor
        val scaledCenterY = centerAdjustmentY * scalingFactor

        println("ReqWidthPx: $reqWidthPx")
        println("ReqHeightPx: $reqHeightPx")

        println("scaledCenterX: $scaledCenterX")
        println("scaledCenterY: $scaledCenterY")

        val centerDeviationFromTargetX = targetCenterX - (scaledCenterX)
        val centerDeviationFromTargetY = targetCenterY - (scaledCenterY)

        println("Center Deviation From Target X: $centerDeviationFromTargetX")
        println("Center deviation From Target Y: $centerDeviationFromTargetY")

        println("Scaled center plus deviation X: ${scaledCenterX + centerDeviationFromTargetX}")
        println("Scaled center plus deviation Y: ${scaledCenterY + centerDeviationFromTargetY}")

        return positions.map {

            println("X is ${it.positionDecimeterX}")
            println("Y is ${it.positionDecimeterY}")
            println("Scaling: $scalingFactor")

            val xScaled = it.positionDecimeterX * scalingFactor
            val yScaled = it.positionDecimeterY * scalingFactor

            println("xScaled: $xScaled")
            println("yScaled: $yScaled")

            println("scaledCenterX = $scaledCenterX")
            println("scaledCenterY = $scaledCenterY")

            val deviationFromCenterX = xScaled - scaledCenterX
            val deviationFromCenterY = yScaled - scaledCenterY

            println("Deviation From Center X: $deviationFromCenterX")
            println("Deviation From Center Y: $deviationFromCenterY")

            val a = Offset(
                x = xScaled - scaledCenterX + (reqWidthPx / 2f),
                y = yScaled - scaledCenterY + (reqHeightPx / 2f)
            )
            println("Offset: $a")
            a
        }
    }

}

