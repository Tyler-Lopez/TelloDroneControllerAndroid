package com.tlopez.controllerDomain

sealed interface TelloCommand {
    object Init : TelloCommand
    object Land : TelloCommand
    data class LeverForce(
        val roll: Int,
        val pitch: Int,
        val throttle: Int,
        val yaw: Int
    ) : TelloCommand

    object Stop : TelloCommand
    object Takeoff : TelloCommand
    object VideoStart : TelloCommand
    object VideoStop : TelloCommand
}