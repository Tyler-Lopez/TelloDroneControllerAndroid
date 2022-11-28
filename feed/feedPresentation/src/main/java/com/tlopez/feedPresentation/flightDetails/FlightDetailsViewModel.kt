package com.tlopez.feedPresentation.flightDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.datastoreDomain.usecase.GetTelloFlightData
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.NavigateUp
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewEvent.ClickedNavigateUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(
    private val getTelloFlightData: GetTelloFlightData,
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<FlightDetailsViewState, FlightDetailsViewEvent, FeedDestination>() {

    val flightId: String = savedStateHandle["flight_id"] ?: error("Missing flight_id")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTelloFlightData(flightId)
                .doOnSuccess {
                    println("Here, success")
                    it.forEach {
                        println(it.timeSinceStartMs)
                    }
                }
        }
    }
    override fun onEvent(event: FlightDetailsViewEvent) {
        when (event) {
            is ClickedNavigateUp -> onClickedNavigateUp()
        }
    }

    private fun onClickedNavigateUp() {
        routeTo(NavigateUp)
    }
}