package com.tlopez.tello_controller.data.repository

import android.content.ComponentName
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat.NV21
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.media.MediaCodec
import android.os.IBinder
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.domain.models.TelloState
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.util.MediaCodecH624
import com.tlopez.tello_controller.util.TelloCommand
import com.tlopez.tello_controller.util.TelloStateUtil
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TelloRepositoryImpl @Inject constructor(
    private val telloStateUtil: TelloStateUtil,
    private val mediaCodecH624: MediaCodecH624
) : TelloRepository {

    private val codec = mediaCodecH624.codec
    private val dataNew = ByteArray(600000)
    private val output = ByteArrayOutputStream()
    private var destPos = 0
    private val startMs = System.currentTimeMillis()
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

    override fun receiveVideoStream(onState: (Bitmap) -> Unit) {
        socketService?.receiveVideoStream {
            System.arraycopy(it.data, it.offset, dataNew, destPos, it.length)
            destPos += it.length
            val pacMan = ByteArray(it.length)
            System.arraycopy(it.data, it.offset, pacMan, 0, it.length)
            val len = it.length
            output.write(pacMan)
            if (len < 1460) {
                destPos = 0
                val data = output.toByteArray()
                output.reset()
                output.flush()
                output.close()
                val inputIndex = codec.dequeueInputBuffer(10000)
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
                val outputIndex = codec.dequeueOutputBuffer(info, 100)
                if (outputIndex >= 0) {
                    val image = codec.getOutputImage(outputIndex)
                    val bm = imgToBom(image!!)
                    onState(bm)
                    codec.releaseOutputBuffer(outputIndex, false)
                }
            }
            println("here receiving video stream")
            this@TelloRepositoryImpl.receiveVideoStream(onState)
        }
    }

    override fun sendTelloCommand(telloCommand: TelloCommand, onResponse: (String) -> Unit) {
        socketService?.sendCommand(telloCommand.command) {
            onResponse(it.decodeToString())
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

        val yuvImage = YuvImage(jm8, NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 75, out)
        val imgBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
    }
}