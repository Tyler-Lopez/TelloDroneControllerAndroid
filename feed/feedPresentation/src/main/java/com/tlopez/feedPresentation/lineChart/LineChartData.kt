package com.tlopez.feedPresentation.lineChart

data class LineChartData(
    val dataSets: List<DataSet>,
    val rangeMaximum: Float,
    val rangeMinimum: Float
) {
    fun DataSet.normalized(): List<Pair<Float, Float>> {
        return dataPoints.map {
            val normalizedData =
                (it.first - dataMinimum) / (dataMaximum - dataMinimum)
            val normalizedRange =
                (it.second - rangeMinimum) / (rangeMaximum - rangeMinimum)
            normalizedData to normalizedRange
        }
    }
}