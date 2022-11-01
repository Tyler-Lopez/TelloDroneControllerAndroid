package com.tlopez.feedPresentation

import com.tlopez.core.architecture.Destination

sealed interface FeedDestination : Destination {
    object NavigateSettings : FeedDestination
}