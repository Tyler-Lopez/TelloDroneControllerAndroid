package com.tlopez.tello_controller.presentation.controller_screen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.domain.usecases.GetTelloStateUseCase
import com.tlopez.tello_controller.domain.usecases.SendTelloCommandUseCase
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import com.tlopez.tello_controller.util.TelloCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val getTelloStateUseCase: GetTelloStateUseCase,
    private val sendTelloCommandUseCase: SendTelloCommandUseCase
) : BaseViewModel<ControllerViewState, ControllerViewEvent>() {

    val state = getTelloStateUseCase()

    init {
    }
    override fun onEvent(event: ControllerViewEvent) {
        when (event) {
            is ClickedConnect -> onClickedConnect()
            is ClickedLand -> onClickedLand()
            is ClickedTakeoff -> onClickedTakeoff()
        }
    }

    private fun onClickedConnect() {
        sendTelloCommandUseCase(TelloCommand.Start)
    }

    private fun onClickedLand() {
        sendTelloCommandUseCase(TelloCommand.Land)
    }

    private fun onClickedTakeoff() {
        sendTelloCommandUseCase(TelloCommand.Takeoff)
    }

}