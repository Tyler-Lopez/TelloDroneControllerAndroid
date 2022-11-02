package com.tlopez.feedPresentation.feed

import com.tlopez.core.architecture.ViewEvent
import com.tlopez.core.architecture.ViewState
import com.tlopez.feedPresentation.NavigationItem

sealed interface FeedViewEvent : ViewEvent {
    object ClickedFly : FeedViewEvent
    object ClickedHome : FeedViewEvent
    object ClickedMyFlights : FeedViewEvent
    object ClickedSettings : FeedViewEvent
}

sealed interface FeedViewState : ViewState {
    val selectedNavigationItem: NavigationItem

    object HomeViewState : FeedViewState {
        override val selectedNavigationItem = NavigationItem.HOME
    }

    object MyFlightsViewState: FeedViewState {
        override val selectedNavigationItem = NavigationItem.FLIGHTS
    }
}