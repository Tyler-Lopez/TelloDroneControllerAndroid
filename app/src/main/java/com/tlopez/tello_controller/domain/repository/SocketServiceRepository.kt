package com.tlopez.tello_controller.domain.repository

import android.content.ServiceConnection
import com.tlopez.tello_controller.util.TelloCommand

interface SocketServiceRepository {
    val serviceConnection: ServiceConnection
    fun sendTelloCommand(telloCommand: TelloCommand)
}