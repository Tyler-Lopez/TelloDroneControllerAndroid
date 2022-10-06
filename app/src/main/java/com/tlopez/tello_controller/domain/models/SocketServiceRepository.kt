package com.tlopez.tello_controller.domain.models

import android.content.ServiceConnection
import androidx.lifecycle.LiveData
import com.tlopez.tello_controller.util.TelloCommand
import kotlinx.coroutines.flow.StateFlow

interface SocketServiceRepository {
    val telloState: StateFlow<ByteArray>
    val telloCommandsResponse: StateFlow<ByteArray>
    val telloVideoStream: StateFlow<ByteArray>
    val serviceConnection: ServiceConnection
    fun sendTelloCommand(telloCommand: TelloCommand)
}