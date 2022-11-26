package com.tlopez.feedPresentation

import com.tlopez.core.architecture.Destination

sealed interface FeedDestination : Destination {
    data class NavigateFlightDetails(val flightId: String) : FeedDestination
    object NavigateFly : FeedDestination
    object NavigateSettings : FeedDestination
    object NavigateUp : FeedDestination
}