package com.tlopez.feedPresentation.flightDetails

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState
import com.tlopez.feedPresentation.lineChart.DataTypeLineChart
import com.tlopez.feedPresentation.lineChart.LineChartData
import com.tlopez.feedPresentation.quadrantGraph.PositionData

sealed interface FlightDetailsViewEvent : ViewEvent {
    object ClickedNavigateUp : FlightDetailsViewEvent
    data class ToggledDataTypeLineChart(val type: DataTypeLineChart) : FlightDetailsViewEvent
}

data class FlightDetailsViewState(
    val lineChartData: LineChartData,
    val positionData: PositionData,
    val selectedDataTypeLineChart: Set<DataTypeLineChart>
) : ViewState