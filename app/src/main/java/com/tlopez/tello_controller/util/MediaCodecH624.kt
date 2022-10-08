package com.tlopez.tello_controller.util

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.media.MediaFormat
import android.media.MediaFormat.*
import android.provider.MediaStore
import java.nio.ByteBuffer

class MediaCodecH624 {

    companion object {
        private const val VIDEO_WIDTH_PX = 960
        private const val VIDEO_HEIGHT_PX = 720
        private val headerSps = byteArrayOf(
            0x00,
            0x00,
            0x00,
            0x01,
            0x67,
            0x42,
            0x00,
            0x0a,
            0xf8.toByte(),
            0x41,
            0xa2.toByte()
        )
        private val headerPps = byteArrayOf(
            0x00,
            0x00,
            0x00,
            0x01,
            0x68,
            0xce.toByte(),
            0x38,
            0x80.toByte()
        )
    }

    val codec: MediaCodec
        get() {
            val format = createVideoFormat(MIMETYPE_VIDEO_AVC, VIDEO_WIDTH_PX, VIDEO_HEIGHT_PX)
            format.setByteBuffer("csd-0", ByteBuffer.wrap(headerSps))
            format.setByteBuffer("csd-1", ByteBuffer.wrap(headerPps))
            format.setInteger(KEY_MAX_INPUT_SIZE, VIDEO_WIDTH_PX * VIDEO_HEIGHT_PX)

            val codecList  =MediaCodecList(MediaCodecList.REGULAR_CODECS)
            codecList.codecInfos.forEach {
                println("here $it")
                println("yup yup ${it.name}")
                println("here here ${it.supportedTypes.joinToString { it }}")
            }
            println("here codec list is $codecList")
                val mydecoder = codecList.findDecoderForFormat(format)
            println("my decoder is $mydecoder")
            val codec = MediaCodec.createByCodecName(mydecoder)
            codec.configure(format, null, null, 0)
            codec.start()
            return codec
        }
}