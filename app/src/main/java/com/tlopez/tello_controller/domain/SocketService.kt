package com.tlopez.tello_controller.domain

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.tlopez.tello_controller.util.TelloCommand
import kotlinx.coroutines.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class SocketService : Service() {

    companion object {
        const val EXTRA_DATA = "extra_data"

        private const val PROTOCOL_INITIALIZE = "command"
        private const val IP_ADDRESS: String = "192.168.10.1"
        private const val UDP_PORT: Int = 8889
    }

    private val address = InetAddress.getByName(IP_ADDRESS)
    private val scope = CoroutineScope(Dispatchers.IO)
    private val socket: DatagramSocket = DatagramSocket()


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

    override fun onBind(intent: Intent?): IBinder? {
        return null
        // TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getParcelableExtra<TelloCommand>(EXTRA_DATA)?.let {
            println(it)
            sendCommand(it.command)
        }
        return START_STICKY
    }
}