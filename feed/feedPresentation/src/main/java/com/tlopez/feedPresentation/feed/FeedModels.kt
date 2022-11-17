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
    object TempClickedInsertChallenge : FeedViewEvent
    object TempClickedTemp : FeedViewEvent
}

sealed interface FeedViewState : ViewState {
    val selectedNavigationItem: NavigationItem

    data class HomeViewState(val testFile: File? = null) : FeedViewState {
        override val selectedNavigationItem = NavigationItem.HOME
    }

    object MyFlightsViewState: FeedViewState {
        override val selectedNavigationItem = NavigationItem.FLIGHTS
    }
}