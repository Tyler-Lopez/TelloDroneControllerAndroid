package com.tlopez.tello_controller.domain.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tlopez.tello_controller.util.TelloCommand
import kotlinx.coroutines.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Bound service
 */
class SocketService : Service() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        private const val IP_ADDRESS: String = "192.168.10.1"
        private const val TAG = "socket_service"
        private const val UDP_PORT: Int = 8889
    }

    private val address = InetAddress.getByName(IP_ADDRESS)
    private val binder by lazy { SocketBinder() }
    private val _telloState: MutableLiveData<Int> = MutableLiveData()
    val telloState: LiveData<Int> = _telloState
    private val scope = CoroutineScope(Dispatchers.IO)
    private val socket: DatagramSocket = DatagramSocket(UDP_PORT)
    private var receiveState: Boolean = false

    fun sendTelloCommand(telloCommand: TelloCommand) {
        sendCommand(telloCommand.command)
    }

    private fun sendCommand(command: String) {
        scope.launch {
            runCatching {
                val commandArr = command.toByteArray()
                val packet = DatagramPacket(
                    commandArr,
                    commandArr.size,
                    address,
                    UDP_PORT
                )
                socket.send(packet)
            }
        }
    }

    private fun receiveResponse() {
        scope.launch {
            runCatching {
                val message = ByteArray(8000)
                val packet = DatagramPacket(
                    message,
                    message.size
                )
                println("attempting to receive")
                socket.soTimeout = 5000
                socket.receive(packet)
                println("Received message see $message")
                message.joinToString { "$it, "}
            }.onSuccess { println("success $it") }
                .onFailure { println("failure $it") }
        }
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getParcelableExtra<TelloCommand>(EXTRA_DATA)?.let {
            println(it)
            sendCommand(it.command)
            receiveResponse()
        }
        return START_STICKY
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