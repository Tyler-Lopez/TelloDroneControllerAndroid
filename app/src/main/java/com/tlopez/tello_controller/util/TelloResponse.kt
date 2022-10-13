package com.tlopez.tello_controller.util

sealed interface TelloResponse {
    object Error : TelloResponse
    object Ok : TelloResponse
}