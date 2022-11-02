package com.tlopez.feedPresentation.feed

import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.*
import com.tlopez.feedPresentation.NavigationItem
import com.tlopez.feedPresentation.feed.FeedViewEvent.*
import com.tlopez.feedPresentation.feed.FeedViewState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
) : BaseRoutingViewModel<FeedViewState, FeedViewEvent, FeedDestination>() {

    init {
        HomeViewState.push()
    }

    override fun onEvent(event: FeedViewEvent) {
        when (event) {
            is ClickedFly -> onClickedFly()
            is ClickedHome -> onClickedHome()
            is ClickedMyFlights -> onClickedMyFlights()
            is ClickedSettings -> onClickedSettings()
        }
    }

    private fun onClickedFly() {
        routeTo(NavigateFly)
    }

    private fun onClickedHome() {
        HomeViewState.push()
    }

    private fun onClickedMyFlights() {
        MyFlightsViewState.push()
    }

    private fun onClickedSettings() {
        routeTo(NavigateSettings)
    }
}

