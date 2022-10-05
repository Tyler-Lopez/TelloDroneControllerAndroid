package com.tlopez.tello_controller.data.repository

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.tlopez.tello_controller.domain.repository.SocketServiceRepository
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.util.TelloCommand
import javax.inject.Singleton

@Singleton
class SocketServiceRepositoryImpl : SocketServiceRepository {

    private var socketService: SocketService? = null

    override val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder: SocketService.SocketBinder = service as SocketService.SocketBinder
            socketService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            socketService = null
        }
    }

    override fun sendTelloCommand(telloCommand: TelloCommand) {
        socketService?.sendTelloCommand(telloCommand)
    }
}