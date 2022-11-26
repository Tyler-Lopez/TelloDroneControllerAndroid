package com.tlopez.feedPresentation.flightDetails

import androidx.lifecycle.SavedStateHandle
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.NavigateUp
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewEvent.ClickedNavigateUp
import javax.inject.Inject

class FlightDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<FlightDetailsViewState, FlightDetailsViewEvent, FeedDestination>() {

    val flightId: String = savedStateHandle["flight_id"] ?: error("Missing flight_id")

    override fun onEvent(event: FlightDetailsViewEvent) {
        when (event) {
            is ClickedNavigateUp -> onClickedNavigateUp()
        }
    }

    private fun onClickedNavigateUp() {
        routeTo(NavigateUp)
    }
}