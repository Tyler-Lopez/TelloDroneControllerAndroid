package com.tlopez.controllerData

import com.tlopez.controllerDomain.TelloCommand
import com.tlopez.controllerDomain.TelloCommand.*

private const val initCommand = "command"
private const val landCommand = "land"
private const val leverForceCommand = "rc %d %d %d %d"
private const val stopCommand = "stop"
private const val takeoffCommand = "takeoff"
private const val videoStartCommand = "streamon"
private const val videoStopCommand = "streamoff"

val TelloCommand.command: String
    get() {
        return when (this) {
            is Init -> initCommand
            is Land -> landCommand
            is LeverForce -> leverForceCommand.format(
                roll, pitch, throttle, yaw
            )
            is Stop -> stopCommand
            is Takeoff -> takeoffCommand
            is VideoStart -> videoStartCommand
            is VideoStop -> videoStopCommand
        }
    }