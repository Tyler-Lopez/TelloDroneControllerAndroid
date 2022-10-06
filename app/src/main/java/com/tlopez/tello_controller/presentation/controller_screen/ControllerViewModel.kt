package com.tlopez.tello_controller.presentation.controller_screen

import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import com.tlopez.tello_controller.util.TelloCommand
import com.tlopez.tello_controller.util.TelloCommand.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository
) : BaseViewModel<ControllerViewState, ControllerViewEvent>() {

    override fun onEvent(event: ControllerViewEvent) {
        when (event) {
            is ClickedConnect -> onClickedConnect()
            is ClickedLand -> onClickedLand()
            is ClickedTakeoff -> onClickedTakeoff()
        }
    }

    private fun onClickedConnect() {
        telloRepository.sendTelloCommand(Start) {}
    }

    private fun onClickedLand() {
        telloRepository.sendTelloCommand(Land) {}
    }

    private fun onClickedTakeoff() {
        telloRepository.sendTelloCommand(Start) {}
    }

}