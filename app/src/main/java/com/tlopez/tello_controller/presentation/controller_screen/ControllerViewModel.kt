package com.tlopez.tello_controller.presentation.controller_screen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import com.tlopez.tello_controller.presentation.thumbstick.ThumbstickState
import com.tlopez.tello_controller.util.TelloCommand
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

    private var leverForce: LeverForce = LeverForce()
        set(value) {
            field = value
            value.apply {
                telloRepository.sendTelloCommand(SetLeverForce(roll, pitch, throttle, yaw)) {}
            }
        }

    init {
        pushState(ControllerViewState(null, ThumbstickState(), ThumbstickState(), null))
    }

    override fun onEvent(event: ControllerViewEvent) {
        when (event) {
            is MovedThrottleYawThumbstick -> onMovedThrottleYawThumbstick(event)
            is MovedRollPitchThumbstick -> onMovedRollPitchThumbstick(event)
            is ResetThrottleYawThumbstick -> onResetThrottleYawThumbstick()
            is ResetRollPitchThumbstick -> onResetRollPitchThumbstick()
            is ClickedBreak -> onClickedStop()
            is ClickedConnect -> onClickedConnect()
            is ClickedLand -> onClickedLand()
            is ClickedTakeoff -> onClickedTakeoff()
            is ClickedStartVideo -> onClickStartVideo()
            else -> {}
        }
    }

    private fun onMovedRollPitchThumbstick(event: MovedRollPitchThumbstick) {
            withLastState {
                val newState = thumbstickRight.copy(
                    fractionHorizontal = (thumbstickRight.fractionHorizontal + event.movedByPercent.x)
                        .coerceIn(-1f..1f),
                    fractionVertical = (thumbstickRight.fractionVertical + event.movedByPercent.y)
                        .coerceIn(-1f..1f)
                )
                leverForce = leverForce.copy(
                    roll = (newState.fractionHorizontal * 100).toInt(),
                    pitch = (newState.fractionVertical * 100).toInt(),
                )
                lastPushedState?.copy(thumbstickRight = newState)?.push()
            }

    }
    private fun onMovedThrottleYawThumbstick(event: MovedThrottleYawThumbstick) {
        withLastState {
            val newState = thumbstickLeft.copy(
                fractionHorizontal = (thumbstickLeft.fractionHorizontal + event.movedByPercent.x)
                    .coerceIn(-1f..1f),
                fractionVertical = (thumbstickLeft.fractionVertical + event.movedByPercent.y)
                    .coerceIn(-1f..1f)
            )
            leverForce = leverForce.copy(
                throttle = (newState.fractionHorizontal * 100).toInt(),
                yaw = (newState.fractionVertical * 100).toInt(),
            )
            lastPushedState?.copy(thumbstickLeft = newState)?.push()
        }
    }

    private fun onResetThrottleYawThumbstick() {
        leverForce = leverForce.copy(throttle = 0, yaw = 0)
        lastPushedState?.copy(thumbstickLeft = ThumbstickState(0f, 0f))?.push()
    }

    private fun onResetRollPitchThumbstick() {
        leverForce = leverForce.copy(roll = 0, pitch = 0)
        lastPushedState?.copy(thumbstickRight = ThumbstickState(0f, 0f))?.push()
    }


    private fun onClickedStop() {
        telloRepository.sendTelloCommand(Start) {
            telloRepository.sendTelloCommand(Brake) {}
        }
    }

    private fun onClickStartVideo() {
        telloRepository.sendTelloCommand(StartVideoStream) {
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

    private data class LeverForce(
        val roll: Int = 0,
        val pitch: Int = 0,
        val throttle: Int = 0,
        val yaw: Int = 0
    )
}