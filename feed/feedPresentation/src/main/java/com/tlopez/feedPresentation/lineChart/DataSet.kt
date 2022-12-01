package com.tlopez.feedPresentation.lineChart

data class DataSet(
    val dataMaximum: Float,
    val dataMinimum: Float,
    val dataPoints: List<Pair<Float, Float>>,
    val dataType: DataTypeLineChart
)