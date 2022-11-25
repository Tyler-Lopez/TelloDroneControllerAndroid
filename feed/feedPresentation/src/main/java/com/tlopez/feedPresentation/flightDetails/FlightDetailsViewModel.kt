package com.tlopez.feedPresentation.flightDetails

import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.feed.FeedViewEvent
import com.tlopez.feedPresentation.feed.FeedViewState
import com.tlopez.storageDomain.usecase.GetUserProfilePictureUrl
import javax.inject.Inject

class FlightDetailsViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val getUserProfilePictureUrl: GetUserProfilePictureUrl
) : BaseRoutingViewModel<FlightDetailsViewState, FlightDetailsViewEvent, FeedDestination>() {
    override fun onEvent(event: FlightDetailsViewEvent) {
        when (event) {
            else -> {}
        }
    }
}