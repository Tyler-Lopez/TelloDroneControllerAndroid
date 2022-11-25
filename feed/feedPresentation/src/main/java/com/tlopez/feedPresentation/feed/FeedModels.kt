package com.tlopez.feedPresentation.feed

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState
import com.tlopez.feedPresentation.NavigationItem
import com.tlopez.feedPresentation.TelloFlightSummary

sealed interface FeedViewEvent : ViewEvent {
    data class ClickedFlightDetails(val flightIndex: Int) : FeedViewEvent
    object ClickedFly : FeedViewEvent
    object ClickedHome : FeedViewEvent
    object ClickedMyFlights : FeedViewEvent
    object ClickedSettings : FeedViewEvent
    object PulledRefresh : FeedViewEvent
}

sealed interface FeedViewState : ViewState {
    val isRefreshing: Boolean
    val selectedNavigationItem: NavigationItem

    fun copyToggleIsRefreshing(): FeedViewState
    fun toHomeViewState(
        flightSummaries: List<TelloFlightSummary>? = null,
        isRefreshing: Boolean? = null
    ): HomeViewState

    fun toMyFlightsViewState(
        isRefreshing: Boolean
    ): MyFlightsViewState

    data class HomeViewState(
        val flightSummaries: List<TelloFlightSummary> = listOf(),
        override val isRefreshing: Boolean = false
    ) : FeedViewState {
        override val selectedNavigationItem = NavigationItem.HOME
        override fun copyToggleIsRefreshing(): HomeViewState =
            copy(isRefreshing = !isRefreshing)

        override fun toHomeViewState(
            flightSummaries: List<TelloFlightSummary>?,
            isRefreshing: Boolean?
        ): HomeViewState = copy(
            flightSummaries = flightSummaries ?: this.flightSummaries,
            isRefreshing = isRefreshing ?: this.isRefreshing
        )

        override fun toMyFlightsViewState(
            isRefreshing: Boolean
        ): MyFlightsViewState = MyFlightsViewState(
            isRefreshing = isRefreshing
        )
    }

    data class MyFlightsViewState(
        override val isRefreshing: Boolean = false
    ) : FeedViewState {
        override val selectedNavigationItem = NavigationItem.FLIGHTS
        override fun copyToggleIsRefreshing(): MyFlightsViewState =
            copy(isRefreshing = !isRefreshing)

        override fun toHomeViewState(
            flightSummaries: List<TelloFlightSummary>?,
            isRefreshing: Boolean?
        ): HomeViewState = HomeViewState(
            flightSummaries = flightSummaries ?: listOf(),
            isRefreshing = isRefreshing ?: this.isRefreshing
        )

        override fun toMyFlightsViewState(
            isRefreshing: Boolean
        ): MyFlightsViewState = copy(
            isRefreshing = isRefreshing
        )
    }
}