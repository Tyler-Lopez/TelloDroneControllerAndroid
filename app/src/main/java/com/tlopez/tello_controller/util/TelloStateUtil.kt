package com.tlopez.tello_controller.util

import com.tlopez.tello_controller.domain.models.TelloState

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

        private const val DELIMITER_CHAR = ";"
    }

    /*
    fun ByteArray.decodeToTelloState(): TelloState {
        val string = decodeToString()
        val argumentArr = string.split(DELIMITER_CHAR)

    }

     */
}