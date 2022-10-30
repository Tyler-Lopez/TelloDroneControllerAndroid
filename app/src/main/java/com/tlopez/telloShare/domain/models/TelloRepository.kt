package com.tlopez.telloShare.domain.models

import android.content.ServiceConnection
import android.graphics.Bitmap
import com.tlopez.telloShare.util.TelloCommand
import com.tlopez.telloShare.util.TelloResponse

interface TelloRepository {
    fun receiveTelloState(onState: (TelloState) -> Unit)
    fun receiveVideoStream(onState: (Bitmap) -> Unit)
    val serviceConnection: ServiceConnection
    fun sendTelloCommand(telloCommand: TelloCommand, onResponse: (TelloResponse) -> Unit)
    fun toggleVideo(onResponse: (Boolean) -> Unit)
}