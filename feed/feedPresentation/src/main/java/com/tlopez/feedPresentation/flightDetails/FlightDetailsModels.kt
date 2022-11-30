package com.tlopez.feedPresentation.flightDetails

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState
import com.tlopez.feedPresentation.lineChart.LineChartData

sealed interface FlightDetailsViewEvent : ViewEvent {
    object ClickedNavigateUp : FlightDetailsViewEvent
}

data class FlightDetailsViewState(
    val heightLineChartData: LineChartData
) : ViewState