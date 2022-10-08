package com.tlopez.tello_controller.util

import android.os.Parcelable
import com.tlopez.tello_controller.domain.models.TelloState
import kotlinx.parcelize.Parcelize


sealed interface TelloCommand {

    val command: String

    companion object {
        private const val FORWARD_COMMAND = "forward %d"
        private const val LAND_COMMAND = "land"
        private const val SET_SPEED_COMMAND = "speed %d"
        private const val MOVEMENT_COMMAND = "rc %d %d %d %d"
        private const val START_COMMAND = "command"
        private const val TAKEOFF_COMMAND = "takeoff"
        private const val START_VIDEO_STREAM_COMMAND = "streamon"
    }

    object Land : TelloCommand {
        override val command: String
            get() = LAND_COMMAND
    }

    object Start : TelloCommand {
        override val command: String
            get() = START_COMMAND
    }

    object Takeoff : TelloCommand {
        override val command: String
            get() = TAKEOFF_COMMAND
    }

    data class Forward(val travelCm: Int) : TelloCommand {
        override val command: String
            get() = FORWARD_COMMAND.format(travelCm)
    }

    data class SetSpeed(val speedCmS: Int) : TelloCommand {
        override val command: String
            get() = FORWARD_COMMAND.format(speedCmS)
    }

    /**
     * @param roll
     * @param pitch
     * @param uplift
     * @param yaw
     */
    data class SetMovement(
        val roll: Int,
        val pitch: Int,
        val uplift: Int,
        val yaw: Int

    ) : TelloCommand {
        override val command: String
            get() {
                return MOVEMENT_COMMAND.format(roll, pitch, uplift, yaw)
            }
    }

    object StartVideoStream : TelloCommand {
        override val command: String
            get() = START_VIDEO_STREAM_COMMAND
    }
}
