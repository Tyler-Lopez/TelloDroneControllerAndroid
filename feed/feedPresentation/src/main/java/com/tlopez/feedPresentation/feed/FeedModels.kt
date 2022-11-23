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

    fun copyToggleIsRefreshing(): FeedViewState
    fun toHomeViewState(
        isRefreshing: Boolean
    ): HomeViewState

    fun toMyFlightsViewState(
        isRefreshing: Boolean
    ): MyFlightsViewState

    data class HomeViewState(
        override val isRefreshing: Boolean = false
    ) : FeedViewState {
        override val selectedNavigationItem = NavigationItem.HOME
        override fun copyToggleIsRefreshing(): HomeViewState =
            copy(isRefreshing = !isRefreshing)

        override fun toHomeViewState(
            isRefreshing: Boolean
        ): HomeViewState = copy(
            isRefreshing = isRefreshing
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
            isRefreshing: Boolean
        ): HomeViewState = HomeViewState(
            isRefreshing = isRefreshing
        )

        override fun toMyFlightsViewState(
            isRefreshing: Boolean
        ): MyFlightsViewState = copy(
            isRefreshing = isRefreshing
        )
    }
}