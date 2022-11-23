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
import com.tlopez.storageDomain.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val storageRepository: StorageRepository
) : BaseRoutingViewModel<FeedViewState, FeedViewEvent, FeedDestination>() {

    private var fetchDataJob: Job? = null

    init {
        HomeViewState().push()
        fetchDataJob = viewModelScope.launch(Dispatchers.IO) {
        }
    }

    override fun onEvent(event: FeedViewEvent) {
        when (event) {
            is ClickedFly -> onClickedFly()
            is ClickedHome -> onClickedHome()
            is ClickedMyFlights -> onClickedMyFlights()
            is ClickedSettings -> onClickedSettings()
            is PulledRefresh -> onPulledRefresh()
            is TempClickedInsertChallenge -> onClickedInsertChallenge()
            is TempClickedTemp -> onTempClickedTemp()
        }
    }

    private fun onClickedFly() {
        routeTo(NavigateFly)
    }

    private fun onClickedHome() {
        fetchDataJob?.cancel()
        lastPushedState?.toHomeViewState(
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

    private fun onPulledRefresh() {
        fetchDataJob?.cancel()
        fetchDataJob = viewModelScope.launch {
            lastPushedState?.copyToggleIsRefreshing()?.push()
            delay(2000)
            lastPushedState?.copyToggleIsRefreshing()?.push()
        }
    }

    private fun onTempClickedTemp() {
        viewModelScope.launch(Dispatchers.IO) {
            //     datastoreRepository.tempQueryAll()
        }
    }
}

