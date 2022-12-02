package com.tlopez.feedPresentation.lineChart

import kotlin.math.abs

data class LineChartData(
    val dataSet: DataSet,
    val rangeMaximum: Float,
    val rangeMinimum: Float
) {
    val dataSetNormalized: List<Pair<Float, Float>> = dataSet.run {
        dataPoints.map {
            val normalizedData =
                (it.first - dataMinimum) / (dataMaximum - dataMinimum)
            val normalizedRange =
                (it.second - rangeMinimum) / (rangeMaximum - rangeMinimum)
            normalizedData to normalizedRange
        }
    }

    fun nearestNormalizedPoint(normalizedValue: Float): Float {
        return dataSetNormalized.minBy {
            abs(normalizedValue - it.second)
        }.second
    }
}