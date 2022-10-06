package com.tlopez.tello_controller.domain.usecases

import com.tlopez.tello_controller.domain.models.SocketServiceRepository
import com.tlopez.tello_controller.util.TelloCommand
import javax.inject.Inject

class SendTelloCommandUseCase @Inject constructor(
    private val socketServiceRepository: SocketServiceRepository
) {
    operator fun invoke(telloCommand: TelloCommand) {
        socketServiceRepository.sendTelloCommand(telloCommand)
    }
}