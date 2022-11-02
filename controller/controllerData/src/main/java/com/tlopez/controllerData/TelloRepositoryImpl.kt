package com.tlopez.controllerData

import android.graphics.Bitmap
import com.tlopez.controllerDomain.TelloCommand
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerDomain.TelloState

class TelloRepositoryImpl : TelloRepository {
    override fun receiveTelloState(onState: (TelloState) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun receiveVideoStream(onState: (Bitmap) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sendTelloCommand(telloCommand: TelloCommand, onResponse: (Result<Unit>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun toggleVideo(onResponse: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }
}