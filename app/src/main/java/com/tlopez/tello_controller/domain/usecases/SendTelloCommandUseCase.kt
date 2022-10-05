package com.tlopez.tello_controller.domain.usecases

import com.tlopez.tello_controller.data.repository.SocketServiceProvider
import com.tlopez.tello_controller.util.TelloCommand
import javax.inject.Inject

class SendTelloCommandUseCase @Inject constructor(
    private val socketServiceProvider: SocketServiceProvider
) {
    operator fun invoke(telloCommand: TelloCommand) {
        socketServiceProvider.socketService?.sendTelloCommand(telloCommand)
    }
}