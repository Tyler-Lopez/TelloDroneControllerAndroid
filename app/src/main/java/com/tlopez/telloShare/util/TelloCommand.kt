package com.tlopez.telloShare.util


sealed interface TelloCommand {

    val command: String

    companion object {
        private const val COMMAND_LAND = "land"
        private const val COMMAND_LEVER_FORCE = "rc %d %d %d %d"
        private const val COMMAND_MOTOR_ON = "motoron"
        private const val COMMAND_STOP = "stop"
        private const val COMMAND_START_SDK_MODE = "command"
        private const val COMMAND_TAKEOFF = "takeoff"
        private const val COMMAND_VIDEO_STREAM_START = "streamon"
        private const val COMMAND_VIDEO_STREAM_STOP = "streamoff"

    }

    object Brake : TelloCommand {
        override val command = COMMAND_STOP
    }

    object Land : TelloCommand {
        override val command = COMMAND_LAND
    }

    /**
     * Set the lever force values for the four channels of the remote control.
     * All values must range from -100 to 100.
     *
     * @param roll Controls left and right movement.
     * @param pitch Controls forward and backward movement.
     * @param throttle Controls altitude of the drone.
     * @param yaw Controls rotational movement.
     */
    data class SetLeverForce(
        val roll: Int,
        val pitch: Int,
        val throttle: Int,
        val yaw: Int
    ) : TelloCommand {
        override val command: String = COMMAND_LEVER_FORCE.format(roll, pitch, throttle, yaw)
    }

    object Start : TelloCommand {
        override val command = COMMAND_START_SDK_MODE
    }

    object MotorOn : TelloCommand {
        override val command = COMMAND_MOTOR_ON
    }

    object Takeoff : TelloCommand {
        override val command = COMMAND_TAKEOFF
    }

    object StartVideoStream : TelloCommand {
        override val command: String = COMMAND_VIDEO_STREAM_START
    }

    object StopVideoStream : TelloCommand {
        override val command: String = COMMAND_VIDEO_STREAM_STOP
    }
}
