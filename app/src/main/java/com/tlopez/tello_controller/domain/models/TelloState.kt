package com.tlopez.tello_controller.domain.models

data class TelloState(
    val missionPadId: Int,
    val missionPadX: Int,
    val missionPadY: Int,
    val missionPadZ: Int,
    val pitch: Int,
    val roll: Int,
    val yaw: Int,
    val speedOfX: Int,
    val speedOfY: Int,
    val speedOfZ: Int,
    val temperatureLowestCelsius: Int,
    val temperatureHighestCelsius: Int,
    val timeOfFlightDistanceCm: Int,
    val heightCm: Int,
    val batteryPercentage: Int,
    val barometerPressureCm: Float,
    val timeMotorUsed: Int,
    val accelerationX: Float,
    val accelerationY: Float,
    val accelerationZ: Float
)