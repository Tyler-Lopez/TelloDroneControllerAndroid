package com.tlopez.tello_controller.domain.usecases

import com.tlopez.tello_controller.domain.models.SocketServiceRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetTelloStateUseCase @Inject constructor(
    private val socketServiceRepository: SocketServiceRepository
) {
    operator fun invoke(): StateFlow<ByteArray> {
        return socketServiceRepository.telloState
    }
}