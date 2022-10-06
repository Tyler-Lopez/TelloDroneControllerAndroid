package com.tlopez.tello_controller.presentation.controller_screen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.domain.models.TelloState
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import com.tlopez.tello_controller.util.TelloCommand
import com.tlopez.tello_controller.util.TelloCommand.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository
) : BaseViewModel<ControllerViewState, ControllerViewEvent>() {

    companion object {
        private const val TELLO_STATE_POLL_DELAY_MS = 1000L
    }

    init {
        pushState(ControllerViewState(null))
        pollTelloStateLoop()
    }

    override fun onEvent(event: ControllerViewEvent) {
        when (event) {
            is ClickedConnect -> onClickedConnect()
            is ClickedForward -> onClickedForward()
            is ClickedLand -> onClickedLand()
            is ClickedSetSpeed -> onClickedSetSpeed()
            is ClickedTakeoff -> onClickedTakeoff()
        }
    }

    private fun onClickedConnect() {
        telloRepository.sendTelloCommand(Start) {}
    }

    private fun onClickedForward() {
        telloRepository.sendTelloCommand(Forward(50)) {}
    }

    private fun onClickedLand() {
        telloRepository.sendTelloCommand(Land) {}
    }

    private fun onClickedSetSpeed() {
        telloRepository.sendTelloCommand(SetSpeed(100)) {}
    }

    private fun onClickedTakeoff() {
        telloRepository.sendTelloCommand(Takeoff) {}
    }

    private fun pollTelloStateLoop() {
        viewModelScope.launch(Dispatchers.IO) {
            pollTelloState()
            delay(TELLO_STATE_POLL_DELAY_MS)
            pollTelloStateLoop()
        }
    }

    private fun pollTelloState() {
        telloRepository.receiveTelloState {
            println("tello state received $it")
            withLastState { copy(telloState = it).push() }
        }
    }

    private inline fun withLastState(block: ControllerViewState.() -> Unit) {
        lastPushedState?.run(block)
    }

}