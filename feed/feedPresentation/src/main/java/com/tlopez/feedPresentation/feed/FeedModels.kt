package com.tlopez.feedPresentation.feed

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState
import com.tlopez.feedPresentation.NavigationItem
import java.io.File

sealed interface FeedViewEvent : ViewEvent {
    object ClickedFly : FeedViewEvent
    object ClickedHome : FeedViewEvent
    object ClickedMyFlights : FeedViewEvent
    object ClickedSettings : FeedViewEvent
    object PulledRefresh : FeedViewEvent
    object TempClickedInsertChallenge : FeedViewEvent
    object TempClickedTemp : FeedViewEvent
}

sealed interface FeedViewState : ViewState {
    val isRefreshing: Boolean
    val selectedNavigationItem: NavigationItem

    data class HomeViewState(
        val fileUrl: String? = null,
        override val isRefreshing: Boolean = false
    ) : FeedViewState {
        override val selectedNavigationItem = NavigationItem.HOME
    }

    data class MyFlightsViewState(override val isRefreshing: Boolean = false): FeedViewState {
        override val selectedNavigationItem = NavigationItem.FLIGHTS
    }
}