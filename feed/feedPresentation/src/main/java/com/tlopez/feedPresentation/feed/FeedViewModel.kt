package com.tlopez.feedPresentation.feed

import androidx.lifecycle.viewModelScope
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.*
import com.tlopez.feedPresentation.TelloFlightSummary
import com.tlopez.feedPresentation.feed.FeedViewEvent.*
import com.tlopez.feedPresentation.feed.FeedViewState.*
import com.tlopez.storageDomain.usecase.GetUserProfilePictureUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val getUserProfilePictureUrl: GetUserProfilePictureUrl,
) : BaseRoutingViewModel<FeedViewState, FeedViewEvent, FeedDestination>() {

    private var fetchDataJob: Job? = null
    private var flightSummaries: List<TelloFlightSummary> = listOf()

    init {
        HomeViewState().push()
        fetchDataJob = viewModelScope.launch(Dispatchers.IO) {
            loadFlights()
        }
    }

    override fun onEvent(event: FeedViewEvent) {
        when (event) {
            is ClickedFlightDetails -> onClickedFlightDetails(event)
            is ClickedFly -> onClickedFly()
            is ClickedHome -> onClickedHome()
            is ClickedMyFlights -> onClickedMyFlights()
            is ClickedSettings -> onClickedSettings()
            is PulledRefresh -> onPulledRefresh()
        }
    }

    private fun onClickedFlightDetails(event: ClickedFlightDetails) {
        routeTo(
            NavigateFlightDetails(
                flightId = flightSummaries[event.flightIndex].flightId
            )
        )
    }

    private fun onClickedFly() {
        routeTo(NavigateFly)
    }

    private fun onClickedHome() {
        fetchDataJob?.cancel()
        lastPushedState?.toHomeViewState(
            flightSummaries = flightSummaries,
            isRefreshing = false
        )?.push()
    }

    private fun onClickedMyFlights() {
        fetchDataJob?.cancel()
        lastPushedState?.toMyFlightsViewState(
            isRefreshing = false
        )?.push()
    }

    private fun onClickedSettings() {
        routeTo(NavigateSettings)
    }

    private fun onPulledRefresh() {
        fetchDataJob?.cancel()
        fetchDataJob = viewModelScope.launch {
            lastPushedState?.copyToggleIsRefreshing()?.push()
            loadFlights()
            lastPushedState?.copyToggleIsRefreshing()?.push()
        }
    }

    private suspend fun loadFlights() {
        datastoreRepository
            .queryTelloFlightsByChallengeOrderedByLength()
            .doOnSuccess { flights ->
                val flightSummaries = flights.map {
                    TelloFlightSummary(
                        profileUrl = getUserProfilePictureUrl(it.owner).getOrNull(),
                        flightId = it.id,
                        flightLength = it.lengthMs.formatFlightLength(),
                        flightStarted = Date(it.startedMs.secondsSinceEpoch * 1000).toString(),
                        username = it.owner
                    )
                }
                this.flightSummaries = flightSummaries
                lastPushedState?.toHomeViewState(
                    flightSummaries = flightSummaries
                )?.push()
            }
            .doOnFailure { println("Failure") }
    }

    private fun Int.formatFlightLength(): String {
        val millisecondsInSecond = 1000f
        return "%.1f s".format(toFloat() / millisecondsInSecond)
    }
}
