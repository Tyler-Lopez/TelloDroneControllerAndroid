package com.tlopez.tello_controller.presentation.controller_screen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import com.tlopez.tello_controller.util.TelloCommand.*
import dagger.hilt.android.lifecycle.HiltViewModel
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
        pushState(ControllerViewState(null, null))

    }

    override fun onEvent(event: ControllerViewEvent) {
        when (event) {
            is ChangedMovement -> onChangedMovement(event)
            is ClickedBreak -> onClickedStop()
            is ClickedConnect -> onClickedConnect()
            is ClickedLand -> onClickedLand()
            is ClickedTakeoff -> onClickedTakeoff()
            is ClickedStartVideo -> onClickStartVideo()
        }
    }

    private fun onChangedMovement(event: ChangedMovement) {
        event.apply {
            val goodRange = -100..100
            telloRepository.sendTelloCommand(SetLeverForce(
                roll.coerceIn(goodRange),
                pitch.coerceIn(goodRange),
                throttle.coerceIn(goodRange),
                yaw.coerceIn(goodRange)
            )) {}
        }
    }

    private fun onClickedStop() {
        telloRepository.sendTelloCommand(Start) {
            telloRepository.sendTelloCommand(Brake) {}
        }
    }

    private fun onClickStartVideo() {
        telloRepository.sendTelloCommand(StartVideoStream) {
            println("response to start video $it")
            pollVideoStream()
        }
    }

    private fun onClickedConnect() {
        telloRepository.sendTelloCommand(Start) {
            telloRepository.sendTelloCommand(StartVideoStream) {
                println("Connection response was $it")
            }
        }
    }

    private fun onClickedLand() {
        telloRepository.sendTelloCommand(Land) {}
    }

    private fun onClickedTakeoff() {
      //  telloRepository.sendTelloCommand(SetSpeed(50)) {}
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
            withLastState { copy(telloState = it).push() }
        }
    }

    private fun pollVideoStream() {
        telloRepository.receiveVideoStream {
            withLastState { copy(latestFrame = it).push() }
        }
    }

    private inline fun withLastState(block: ControllerViewState.() -> Unit) {
        lastPushedState?.run(block)
    }

}