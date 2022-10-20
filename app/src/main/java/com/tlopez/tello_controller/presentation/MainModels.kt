package com.tlopez.tello_controller.presentation

import com.tlopez.tello_controller.architecture.Destination

sealed interface MainDestination : Destination {
    object NavigateController : MainDestination
}