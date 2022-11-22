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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val storageRepository: StorageRepository
) : BaseRoutingViewModel<FeedViewState, FeedViewEvent, FeedDestination>() {

    init {
        HomeViewState().push()
        viewModelScope.launch(Dispatchers.IO) {
            storageRepository.downloadFile("beaker.jpg")
                .doOnSuccess {
                    println("here, success")
                    HomeViewState(it.toString()).push()
                }
                .doOnFailure {
                    println("here, failure")
                    println("$it")
                }
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
        HomeViewState().push()
    }

    private fun onClickedMyFlights() {
        MyFlightsViewState().push()
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
        viewModelScope.launch {
            (lastPushedState as? HomeViewState)?.run {
                copy(isRefreshing = true).push()
                delay(2000)
                copy(isRefreshing = false).push()
            }
        }
    }

    private fun onTempClickedTemp() {
        viewModelScope.launch(Dispatchers.IO) {
            //     datastoreRepository.tempQueryAll()
        }
    }
}

