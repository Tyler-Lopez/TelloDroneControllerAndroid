package com.tlopez.feedPresentation

data class TelloFlightSummary(
    val flightId: String,
    val flightLength: String,
    val flightStarted: String,
    val profileUrl: String?,
    val username: String
)
