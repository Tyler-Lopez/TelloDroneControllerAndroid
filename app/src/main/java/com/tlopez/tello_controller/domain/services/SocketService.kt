package com.tlopez.tello_controller.domain.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tlopez.tello_controller.util.TelloCommand
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Bound service
 */
class SocketService : Service() {

    companion object {
        private const val IP_ADDRESS: String = "192.168.10.1"
        private const val TAG = "socket_service"
        private const val UDP_PORT_COMMANDS: Int = 8889
        private const val UDP_PORT_STATE: Int = 8890
        private const val UDP_PORT_VIDEO: Int = 11111
    }

    private val address = InetAddress.getByName(IP_ADDRESS)
    private val binder by lazy { SocketBinder() }
    private val scope = CoroutineScope(Dispatchers.IO)
    private val socketCommands: DatagramSocket = DatagramSocket(UDP_PORT_COMMANDS)
    private val socketState: DatagramSocket = DatagramSocket(UDP_PORT_STATE)
    private val socketVideoStream: DatagramSocket = DatagramSocket(UDP_PORT_VIDEO)

    fun receiveTelloState(onState: (ByteArray) -> Unit) {
        socketState.receiveResponse(onState)
    }

    fun receiveVideoStream(onVideoStream: (DatagramPacket) -> Unit) {
        val message = ByteArray(2048)
        val packet = DatagramPacket(
            message,
            message.size
        )
        scope.launch {
            runCatching {
                socketVideoStream.soTimeout = 5000
                socketVideoStream.receive(packet)
            }.onSuccess {
                onVideoStream(packet)
            }.onFailure {
                println("whoops failure $it")
            }
        }
    }

    fun sendCommand(command: String, onResponse: (ByteArray) -> Unit) {
        scope.launch {
            runCatching {
                val commandArr = command.toByteArray()
                val packet = DatagramPacket(
                    commandArr,
                    commandArr.size,
                    address,
                    UDP_PORT_COMMANDS
                )
                socketCommands.send(packet)
                socketCommands.receiveResponse(onResponse)
            }
        }
    }

    fun sendCommandWithoutResponse(command: String) {
        scope.launch {
            runCatching {
                val commandArr = command.toByteArray()
                val packet = DatagramPacket(
                    commandArr,
                    commandArr.size,
                    address,
                    UDP_PORT_COMMANDS
                )
                socketCommands.send(packet)
            }
        }
    }

    private fun DatagramSocket.receiveResponse(onSuccess: (ByteArray) -> Unit) {
        val message = ByteArray(60000)
        scope.launch {
            runCatching {
                val packet = DatagramPacket(
                    message,
                    message.size
                )
                soTimeout = 5000
                receive(packet)
            }.onSuccess {
                onSuccess(message)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    /**
     * Custom class which extends [Binder].
     * Returns a Singleton of this Service class.
     *
     * Provides an access point for retrieving this service.
     */
    inner class SocketBinder : Binder() {
        fun getService(): SocketService = this@SocketService
    }
}