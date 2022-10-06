package com.tlopez.tello_controller.data.repository

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.Observer
import com.tlopez.tello_controller.domain.models.SocketServiceRepository
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.util.TelloCommand
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Singleton
class SocketServiceRepositoryImpl : SocketServiceRepository {

    private var socketService: SocketService? = null
    private val socketServiceStateObserver: Observer<ByteArray> = Observer {
        _telloState.value = it
    }
    private val socketServiceCommandsResponseObserver: Observer<ByteArray> = Observer {
        _telloCommandsResponse.value = it
    }
    private val socketServiceVideoStreamObserver: Observer<ByteArray> = Observer {
        _telloVideoStream.value = it
    }
    private val _telloCommandsResponse = MutableStateFlow(byteArrayOf())
    override val telloCommandsResponse = _telloCommandsResponse
    private val _telloState = MutableStateFlow(byteArrayOf())
    override val telloState = _telloState
    private val _telloVideoStream = MutableStateFlow(byteArrayOf())
    override val telloVideoStream = _telloVideoStream

    override val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder: SocketService.SocketBinder = service as SocketService.SocketBinder
            socketService = binder.getService()
            socketService?.telloState?.observeForever(socketServiceStateObserver)
            socketService?.telloCommandsResponse?.observeForever(socketServiceCommandsResponseObserver)
            socketService?.telloVideoStream?.observeForever(socketServiceVideoStreamObserver)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            socketService?.telloState?.removeObserver(socketServiceStateObserver)
            socketService?.telloCommandsResponse?.removeObserver(socketServiceCommandsResponseObserver)
            socketService?.telloVideoStream?.removeObserver(socketServiceVideoStreamObserver)
            socketService = null
        }
    }

    override fun sendTelloCommand(telloCommand: TelloCommand) {
        socketService?.sendTelloCommand(telloCommand)
    }
}