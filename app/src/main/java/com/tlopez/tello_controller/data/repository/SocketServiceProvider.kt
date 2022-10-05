package com.tlopez.tello_controller.data.repository

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.tlopez.tello_controller.domain.services.SocketService

class SocketServiceProvider : ServiceConnection {

    var socketService: SocketService? = null

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder: SocketService.SocketBinder = service as SocketService.SocketBinder
        socketService = binder.getService()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        socketService = null
    }
}