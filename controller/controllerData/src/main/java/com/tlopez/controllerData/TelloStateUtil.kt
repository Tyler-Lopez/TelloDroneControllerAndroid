package com.tlopez.controllerData

import com.tlopez.controllerDomain.TelloState

class TelloStateUtil {
    companion object {
        private const val ACCELERATION_X = "agx"
        private const val ACCELERATION_Y = "agy"
        private const val ACCELERATION_Z = "agz"
        private const val BATTERY = "bat"
        private const val BAROMETER = "baro"
        private const val HEIGHT = "h"
        private const val MISSION_PAD_ID = "mid"
        private const val MISSION_PAD_X = "x"
        private const val MISSION_PAD_Y = "y"
        private const val MISSION_PAD_Z = "z"
        private const val PITCH = "pitch"
        private const val ROLL = "roll"
        private const val SPEED_X = "vgx"
        private const val SPEED_Y = "vgy"
        private const val SPEED_Z = "vgz"
        private const val TEMPERATURE_LOWEST = "templ"
        private const val TEMPERATURE_HIGHEST = "temph"
        private const val TIME_OF_FLIGHT_DISTANCE = "tof"
        private const val TIME = "time"
        private const val YAW = "yaw"
        private const val DELIMITER_CHAR = ';'
        private const val SUB_DELIMITER_CHAR = ':'
    }

    fun decodeToTelloState(byteArray: ByteArray): TelloState {
        val string = byteArray.decodeToString()
        val argumentArr = string.split(DELIMITER_CHAR)
        val telloStateMap = mutableMapOf<String, String>()
        argumentArr.forEach {
            it.split(SUB_DELIMITER_CHAR).apply {
                telloStateMap[first()] = last()
            }
        }
        return object : TelloState {
            override val missionPadId = telloStateMap[MISSION_PAD_ID]?.toInt()
                ?: error("Error missing MissionPadId")
            override val missionPadX = telloStateMap[MISSION_PAD_X]?.toInt()
                ?: error("Error missing MissionPadX")
            override val missionPadY = telloStateMap[MISSION_PAD_Y]?.toInt()
                ?: error("Error missing MissionPadY")
            override val missionPadZ = telloStateMap[MISSION_PAD_Z]?.toInt()
                ?: error("Error missing MissionPadZ")
            override val pitch = telloStateMap[PITCH]?.toInt()
                ?: error("Error missing Pitch")
            override val roll = telloStateMap[ROLL]?.toInt()
                ?: error("Error missing Roll")
            override val yaw = telloStateMap[YAW]?.toInt()
                ?: error("Error missing Yaw")
            override val speedOfX = telloStateMap[SPEED_X]?.toInt()
                ?: error("Error missing SpeedX")
            override val speedOfY = telloStateMap[SPEED_Y]?.toInt()
                ?: error("Error missing SpeedY")
            override val speedOfZ = telloStateMap[SPEED_Z]?.toInt()
                ?: error("Error missing SpeedZ")
            override val temperatureLowestCelsius = telloStateMap[TEMPERATURE_LOWEST]?.toInt()
                ?: error("Error missing TemperatureLowest")
            override val temperatureHighestCelsius = telloStateMap[TEMPERATURE_HIGHEST]?.toInt()
                ?: error("Error missing TemperatureHighest")
            override val timeOfFlightDistanceCm =
                telloStateMap[TIME_OF_FLIGHT_DISTANCE]?.toInt()
                    ?: error("Error missing TimeOfFlightDistance")
            override val heightCm = telloStateMap[HEIGHT]?.toInt()
                ?: error("Error missing Height")
            override val batteryPercentage = telloStateMap[BATTERY]?.toInt()
                ?: error("Error missing Battery")
            override val barometerPressureCm = telloStateMap[BAROMETER]?.toFloat()
                ?: error("Error missing Barometer")
            override val timeMotorUsed = telloStateMap[TIME]?.toInt()
                ?: error("Error missing Time motor used")
            override val accelerationX = telloStateMap[ACCELERATION_X]?.toFloat()
                ?: error("Error missing AccelerationX")
            override val accelerationY = telloStateMap[ACCELERATION_Y]?.toFloat()
                ?: error("Error missing AccelerationY")
            override val accelerationZ = telloStateMap[ACCELERATION_Z]?.toFloat()
                ?: error("Error missing AccelerationZ")
        }
    }
}