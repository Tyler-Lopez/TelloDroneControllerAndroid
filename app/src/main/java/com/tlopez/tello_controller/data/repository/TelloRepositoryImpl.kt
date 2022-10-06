package com.tlopez.tello_controller.data.repository

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.domain.models.TelloState
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.util.TelloCommand
import com.tlopez.tello_controller.util.TelloStateUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TelloRepositoryImpl @Inject constructor(
    private val telloStateUtil: TelloStateUtil
) : TelloRepository {

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

    override fun receiveTelloState(onState: (TelloState) -> Unit) {
        socketService?.receiveTelloState {
            telloStateUtil.run { it.decodeToTelloState() }?.let {
                onState(it)
        }
    }
}

override fun sendTelloCommand(telloCommand: TelloCommand, onResponse: (String) -> Unit) {
    socketService?.sendCommand(telloCommand.command) {
        onResponse(it.decodeToString())
    }
}
}