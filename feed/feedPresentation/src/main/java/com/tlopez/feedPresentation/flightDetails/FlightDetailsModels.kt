package com.tlopez.feedPresentation.flightDetails

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState

sealed interface FlightDetailsViewEvent : ViewEvent {
    object ClickedNavigateUp : FlightDetailsViewEvent
}

data class FlightDetailsViewState(
    val temp: String
) : ViewState