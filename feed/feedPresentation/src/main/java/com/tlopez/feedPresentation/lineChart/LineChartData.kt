package com.tlopez.feedPresentation.lineChart

data class LineChartData<TypeOfDataNumber : Number, TypeOfRangeNumber : Number>(
    val dataRangePoints: List<Pair<TypeOfDataNumber, TypeOfRangeNumber>>,
    val dataMaximum: TypeOfDataNumber,
    val dataMinimum: TypeOfDataNumber,
    val rangeMaximum: TypeOfRangeNumber,
    val rangeMinimum: TypeOfRangeNumber
) {
    val normalizedDataRangePoints: List<Pair<Float, Float>> = dataRangePoints.map {
        val dataMaximumFloat = dataMaximum.toFloat()
        val dataMinimumFloat = dataMinimum.toFloat()
        val rangeMaximumFloat = rangeMaximum.toFloat()
        val rangeMinimumFloat = rangeMinimum.toFloat()

        val normalizedData = (it.first.toFloat() - dataMinimumFloat) / (dataMaximumFloat - dataMinimumFloat)
        val normalizedRange = (it.second.toFloat() - rangeMinimumFloat) / (rangeMaximumFloat - rangeMinimumFloat)

        normalizedData to normalizedRange
    }
}