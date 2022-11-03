package com.tlopez.controllerData

import android.graphics.Bitmap
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerDomain.TelloResponse
import com.tlopez.controllerDomain.TelloState
import java.lang.String.format
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class TelloRepositoryImpl @Inject constructor(
    private val telloStateUtil: TelloStateUtil
) : TelloRepository {

    companion object {
        private const val COMMAND_CONNECT = "command"
        private const val COMMAND_LAND = "land"
        private const val COMMAND_LEVER_FORCE = "rc %d %d %d %d"
        private const val COMMAND_TAKE_OFF = "takeoff"
        private const val COMMAND_VIDEO_START = "streamon"
        private const val COMMAND_VIDEO_STOP = "streamoff"

        private const val IP_ADDRESS: String = "192.168.10.1"
        private const val UDP_PORT_COMMANDS: Int = 8889
        private const val UDP_PORT_STATE: Int = 8890
        private const val UDP_PORT_VIDEO: Int = 11111
    }

    private val address = InetAddress.getByName(IP_ADDRESS)
    private val socketCommands = DatagramSocket(UDP_PORT_COMMANDS)
    private val socketState = DatagramSocket(UDP_PORT_STATE)
    private val socketVideoStream = DatagramSocket(UDP_PORT_VIDEO)
    private var videoBitmapListener: ((Bitmap) -> Unit)? = null

    override suspend fun connect(): Result<TelloResponse> {
        return socketCommands.sendCommandWithResponse(COMMAND_CONNECT)
    }

    override suspend fun land(): Result<TelloResponse> {
        return socketCommands.sendCommandWithResponse(COMMAND_LAND)
    }

    override suspend fun receiveTelloState(): Result<TelloState> {
        return try {
            success(
                telloStateUtil
                    .decodeToTelloState(
                        byteArray = socketState
                            .receiveResponse()
                            .getOrThrow()
                    )
            )
        } catch (e: Exception) {
            failure(e)
        }
    }

    override suspend fun setVideoBitmapListener(onBitmap: (Bitmap) -> Unit) {
        videoBitmapListener = onBitmap
    }

    override suspend fun setLeverForce(
        roll: Int,
        pitch: Int,
        throttle: Int,
        yaw: Int
    ): Result<Unit> {
        return socketCommands.sendCommand(
            format(COMMAND_LEVER_FORCE, roll, pitch, throttle, yaw)
        )
    }

    override suspend fun takeOff(): Result<TelloResponse> {
        return socketCommands.sendCommandWithResponse(COMMAND_TAKE_OFF)
    }

    override suspend fun videoStart(): Result<TelloResponse> {
        return socketCommands.sendCommandWithResponse(COMMAND_VIDEO_START)
    }

    override suspend fun videoStop(): Result<TelloResponse> {
        return socketCommands.sendCommandWithResponse(COMMAND_VIDEO_STOP)
    }

    private fun DatagramSocket.sendCommand(command: String): Result<Unit> {
        return try {
            val commandArr = command.toByteArray()
            val packet = DatagramPacket(
                commandArr,
                commandArr.size,
                address,
                UDP_PORT_COMMANDS
            )
            socketCommands.send(packet)
            success(Unit)
        } catch (e: Exception) {
            failure(e)
        }
    }

    private fun DatagramSocket.sendCommandWithResponse(command: String): Result<TelloResponse> {
        return try {
            sendCommand(command)
            receiveResponse()
            // todo actually parse
            success(TelloResponse.OK)
        } catch (e: Exception) {
            failure(e)
        }
    }

    private fun DatagramSocket.receiveResponse(): Result<ByteArray> {
        val message = ByteArray(1024)
        return try {
            val packet = DatagramPacket(
                message,
                message.size
            )
            soTimeout = 5000
            receive(packet)
            success(message)
        } catch (e: Exception) {
            failure(e)
        }
    }
}