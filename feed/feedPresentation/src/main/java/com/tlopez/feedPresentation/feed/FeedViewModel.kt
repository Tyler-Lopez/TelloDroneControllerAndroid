package com.tlopez.feedPresentation.feed

import androidx.lifecycle.viewModelScope
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.*
import com.tlopez.feedPresentation.feed.FeedViewEvent.*
import com.tlopez.feedPresentation.feed.FeedViewState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository
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
            is TempClickedInsertChallenge -> onClickedInsertChallenge()
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

    private fun onClickedInsertChallenge() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.insertChallenge("Test")
                .doOnSuccess {
                    println("success")
                }
                .doOnFailure {
                    println("failure")
                }
        }
    }
}

