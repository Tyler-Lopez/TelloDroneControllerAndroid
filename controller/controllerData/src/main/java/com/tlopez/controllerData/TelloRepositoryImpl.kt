package com.tlopez.controllerData

import android.graphics.*
import android.media.Image
import android.media.MediaCodec
import android.provider.ContactsContract
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerDomain.TelloResponse
import com.tlopez.controllerDomain.TelloState
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.lang.String.format
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.ByteBuffer
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TelloRepositoryImpl @Inject constructor(
    private val telloStateUtil: TelloStateUtil,
    private val ioDispatcher: CoroutineDispatcher,
    private val codec: MediaCodec
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
                            .receiveResponseAsByteArray()
                            .getOrThrow()
                    )
            )
        } catch (e: Exception) {
            println("Exception caught receiving tello state $e")
            failure(e)
        }
    }

    override suspend fun setVideoBitmapListener(onBitmap: ((Bitmap) -> Unit)?) {
        videoBitmapListener = onBitmap
    }

    override suspend fun setLeverForce(
        roll: Int,
        pitch: Int,
        throttle: Int,
        yaw: Int
    ): Result<Unit> {
        return sendCommand(
            format(COMMAND_LEVER_FORCE, roll, pitch, throttle, yaw)
        )
    }

    override suspend fun takeOff(): Result<TelloResponse> {
        return socketCommands.sendCommandWithResponse(COMMAND_TAKE_OFF)
    }

    override suspend fun videoStart(): Result<TelloResponse> {
        return socketCommands.sendCommandWithResponse(COMMAND_VIDEO_START)
            .doOnSuccess {
                if (it == TelloResponse.OK) {
                    receiveVideoLoop()
                }
            }
    }

    override suspend fun videoStop(): Result<TelloResponse> {
        return socketCommands.sendCommandWithResponse(COMMAND_VIDEO_STOP)
            .doOnSuccess {
                ioDispatcher[Job]?.cancel()

            }
    }

    private fun sendCommand(command: String): Result<Unit> {
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
            val responseStr = receiveResponseAsByteArray()
                .getOrThrow()
                .decodeToStringRemoveTrailingZero()
                .toTelloResponse()
            success(responseStr)
        } catch (e: Exception) {
            failure(e)
        }
    }

    private fun DatagramSocket.receiveResponseAsByteArray(): Result<ByteArray> {
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

    private fun DatagramSocket.receiveResponseAsDatagramPacket(): Result<DatagramPacket> {
        val message = ByteArray(2048)
        return try {
            val packet = DatagramPacket(
                message,
                message.size
            )
            soTimeout = 5000
            receive(packet)
            success(packet)
        } catch (e: Exception) {
            failure(e)
        }
    }

    private fun ByteArray.decodeToStringRemoveTrailingZero(): String {
        val endIndex: Int = indexOf(0)
        return decodeToString(0, endIndex)
    }

    private fun String.toTelloResponse(): TelloResponse {
        return when (this) {
            "ok" -> TelloResponse.OK
            "error" -> TelloResponse.ERROR
            else -> error("Unexpected response [$this]!")
        }
    }

    private val dataNew = ByteArray(600000)
    private val output = ByteArrayOutputStream()
    private var destPos = 0
    private val startMs = System.currentTimeMillis()

    private var videoFuckingJob: Job? = null

    private fun receiveVideoLoop() {
        socketVideoStream
            .receiveResponseAsDatagramPacket()
            .doOnSuccess {
                println("HERE FUCKING SUCCESS")
                it.parseVideoLoopResponse()
                receiveVideoLoop()
            }
            .doOnFailure {
                println("HERE FUCKING FAILURE $it")
            }
    }

    private fun DatagramPacket.parseVideoLoopResponse() {
        System.arraycopy(data, offset, dataNew, destPos, length)
        destPos += length
        val pacMan = ByteArray(length)
        System.arraycopy(data, offset, pacMan, 0, length)
        val len = length
        output.write(pacMan)
        if (len < 1460) {
            destPos = 0
            val data = output.toByteArray()
            output.reset()
            output.flush()
            output.close()
            val inputIndex = try {
                codec.dequeueInputBuffer(10000)
            } catch (e: IllegalStateException) {
                println("CAUGHT ERROR INPUT BUFFER")
                -1
            }
            if (inputIndex >= 0) {
                val buffer = codec.getInputBuffer(inputIndex)
                if (buffer != null) {
                    buffer.clear()
                    buffer.put(data)
                    val presTime = System.currentTimeMillis() - startMs
                    codec.queueInputBuffer(inputIndex, 0, data.size, presTime, 0)
                }
            }

            val info = MediaCodec.BufferInfo()
            val outputIndex = try {
                codec.dequeueOutputBuffer(info, 100)
            } catch (e: IllegalStateException) {
                println("CAUGHT ERROR OUTPUT BUFFER")
                -1
            }
            if (outputIndex >= 0) {
                val image = codec.getOutputImage(outputIndex)
                val bm = imgToBom(image!!)
                videoBitmapListener?.invoke(bm)
                codec.releaseOutputBuffer(outputIndex, false)
            }
        }
    }


    private fun imgToBom(image: Image): Bitmap {
        val p = image.planes
        val y: ByteBuffer = p[0].buffer
        val u = p[1].buffer
        val v = p[2].buffer

        val ySz = y.remaining()
        val uSz = u.remaining()
        val vSz = v.remaining()

        val jm8 = ByteArray(ySz + uSz + vSz)
        y.get(jm8, 0, ySz)
        v.get(jm8, ySz, vSz)
        u.get(jm8, ySz + vSz, uSz)

        val yuvImage = YuvImage(jm8, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 75, out)
        val imgBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
    }
}