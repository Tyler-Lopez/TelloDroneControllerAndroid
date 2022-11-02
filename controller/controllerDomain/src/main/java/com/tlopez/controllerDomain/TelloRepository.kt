package com.tlopez.controllerDomain

import android.graphics.Bitmap

interface TelloRepository {
    fun receiveTelloState(onState: (TelloState) -> Unit)
    fun receiveVideoStream(onState: (Bitmap) -> Unit)
    fun sendTelloCommand(telloCommand: TelloCommand, onResponse: (Result<Unit>) -> Unit)
    fun toggleVideo(onResponse: (Boolean) -> Unit)
}