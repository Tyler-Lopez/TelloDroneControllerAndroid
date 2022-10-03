package com.tlopez.tello_controller.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed interface TelloCommand : Parcelable {

    val command: String

    companion object {
        private const val LAND_COMMAND = "land"
        private const val START_COMMAND = "command"
        private const val TAKEOFF_COMMAND = "takeoff"
    }

    @Parcelize
    object Land : TelloCommand {
        override val command: String
            get() = LAND_COMMAND
    }

    @Parcelize
    object Start : TelloCommand {
        override val command: String
            get() = START_COMMAND
    }

    @Parcelize
    object Takeoff : TelloCommand {
        override val command: String
            get() = TAKEOFF_COMMAND
    }
}
