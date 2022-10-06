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
        const val EXTRA_DATA = "extra_data"
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
    private val _telloCommandsResponse = MutableLiveData<ByteArray>()
    val telloCommandsResponse: LiveData<ByteArray> = _telloCommandsResponse
    private val _telloState = MutableLiveData<ByteArray>()
    val telloState: LiveData<ByteArray> = _telloState
    private val _telloVideoStream = MutableLiveData<ByteArray>()
    val telloVideoStream: LiveData<ByteArray> = _telloVideoStream
    private var stateLoopJob: Job? = null

    fun sendTelloCommand(telloCommand: TelloCommand) {
        sendCommand(telloCommand.command)
    }

    fun coroutineLoop() {
        stateLoopJob?.cancel()
        stateLoopJob = scope.launch {
            delay(1000)
            socketState.receiveResponse(
                onSuccess = {
                    _telloState.postValue(it)
                    coroutineLoop()
                }
            )
        }
    }

    private fun sendCommand(command: String) {
        scope.launch {
            runCatching {
                coroutineLoop()
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
        val message = ByteArray(8000)
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getParcelableExtra<TelloCommand>(EXTRA_DATA)?.let {
            sendCommand(it.command)
            socketCommands.receiveResponse { response ->
                _telloCommandsResponse.postValue(response)
            }
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