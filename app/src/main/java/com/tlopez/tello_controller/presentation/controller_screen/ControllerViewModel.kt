package com.tlopez.tello_controller.presentation.controller_screen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import com.tlopez.tello_controller.presentation.thumbstick.ThumbstickState
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewState.*
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewState.ConnectedViewState.*
import com.tlopez.tello_controller.util.TelloCommand
import com.tlopez.tello_controller.util.TelloCommand.*
import com.tlopez.tello_controller.util.TelloResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository
) : BaseViewModel<ControllerViewState, ControllerViewEvent>() {

    companion object {
        private const val POLL_DELAY_MS_FLIGHT_TIME = 100L
        private const val POLL_DELAY_MS_CONNECTION_CHECK = 500L
    }

    private var flightStartedUnixMs: Long = 0
    private var leverForce: LeverForce = LeverForce()
        set(value) {
            field = value
            value.apply {
                telloRepository.sendTelloCommand(SetLeverForce(roll, pitch, throttle, yaw)) {}
            }
        }
    private var pollConnectionJob: Job? = null
    private var pollFlightTimeJob: Job? = null

    init {
        DisconnectedIdle.push()
    }

    override fun onEvent(event: ControllerViewEvent) {
        when (event) {
            is ClickedConnect -> onClickedConnect()
            is ClickedLand -> onClickedLand()
            is ClickedTakeoff -> onClickedTakeoff()
            is MovedThrottleYawThumbstick -> onMovedThrottleYawThumbstick(event)
            is MovedRollPitchThumbstick -> onMovedRollPitchThumbstick(event)
            is ResetThrottleYawThumbstick -> onResetThrottleYawThumbstick()
            is ResetRollPitchThumbstick -> onResetRollPitchThumbstick()
            is ToggleVideo -> onToggleVideo()
        }
    }

    private fun onMovedRollPitchThumbstick(event: MovedRollPitchThumbstick) {
        withLastStateAsFlying {
            val newState = rollPitchThumbstickState.run {
                copy(
                    fractionHorizontal = (fractionHorizontal + event.movedByPercent.x)
                        .coerceIn(-1f..1f),
                    fractionVertical = (fractionVertical + event.movedByPercent.y)
                        .coerceIn(-1f..1f)
                )
            }
            leverForce = leverForce.copy(
                roll = (newState.fractionHorizontal * 100).toInt(),
                pitch = (newState.fractionVertical * 100).toInt(),
            )
            copy(rollPitchThumbstickState = newState).push()
        }
    }

    private fun onMovedThrottleYawThumbstick(event: MovedThrottleYawThumbstick) {
        withLastStateAsFlying {
            val newState = throttleYawThumbstickState.run {
                copy(
                    fractionHorizontal = (fractionHorizontal + event.movedByPercent.x)
                        .coerceIn(-1f..1f),
                    fractionVertical = (fractionVertical + event.movedByPercent.y)
                        .coerceIn(-1f..1f)
                )
            }
            leverForce = leverForce.copy(
                throttle = (newState.fractionHorizontal * 100).toInt(),
                yaw = (newState.fractionVertical * 100).toInt(),
            )
            copy(throttleYawThumbstickState = newState).push()
        }
    }

    private fun onResetThrottleYawThumbstick() {
        leverForce = leverForce.copy(throttle = 0, yaw = 0)
        withLastStateAsFlying {
            copy(throttleYawThumbstickState = ThumbstickState(0f, 0f)).push()
        }
    }

    private fun onResetRollPitchThumbstick() {
        leverForce = leverForce.copy(roll = 0, pitch = 0)
        withLastStateAsFlying {
            copy(rollPitchThumbstickState = ThumbstickState(0f, 0f)).push()
        }
    }

    private fun onClickedConnect() {
        telloRepository.sendTelloCommand(Start) {
            if (it is TelloResponse.Ok) {
                ConnectedIdle().push()
                pollConnectionLoop()
            }
        }
    }

    private fun onClickedLand() {
        telloRepository.sendTelloCommand(Land) {
            if (it is TelloResponse.Ok) {
                pollFlightTimeJob?.cancel()
                withLastStateAsFlying {
                    ConnectedIdle(
                        latestFrame = latestFrame,
                        videoOn = videoOn,
                        lastFlightMs = System.currentTimeMillis() - flightStartedUnixMs
                    ).push()
                }
            }
        }
    }

    private fun onClickedTakeoff() {
        withLastStateAsConnected {
            TakingOff(latestFrame, videoOn).push()
        }
        telloRepository.sendTelloCommand(Takeoff) {
            if (it is TelloResponse.Ok) {
                flightStartedUnixMs = System.currentTimeMillis()
                pollFlightTimeJob?.cancel()
                pollFlightTimeLoop()
                withLastStateAsConnected {
                    Flying(latestFrame, videoOn).push()
                }
            }
        }
    }

    private fun onToggleVideo() {
        telloRepository.sendTelloCommand(StartVideoStream) {
            pollVideoStream()
        }
    }

    private fun pollConnectionLoop() {
        pollConnectionJob?.cancel()
        pollConnectionJob = viewModelScope.launch(Dispatchers.IO) {
            pollConnection()
            delay(POLL_DELAY_MS_CONNECTION_CHECK)
            pollConnectionLoop()
        }
    }

    private fun pollConnection() {
        telloRepository.sendTelloCommand(Start) {
            if (it is TelloResponse.Error) {
                DisconnectedError.push()
            }
        }
    }

    private fun pollFlightTimeLoop() {
        pollFlightTimeJob = viewModelScope.launch(Dispatchers.IO) {
            pollFlightTime()
            delay(POLL_DELAY_MS_FLIGHT_TIME)
            pollFlightTimeLoop()
        }
    }

    private fun pollFlightTime() {
        withLastStateAsFlying {
            copy(flightLengthMs = System.currentTimeMillis() - flightStartedUnixMs).push()
        }
    }

    private fun pollVideoStream() {

    }

    private inline fun withLastStateAsConnected(block: ConnectedViewState.() -> Unit) {
        (lastPushedState as? ConnectedViewState)?.run(block)
    }

    private inline fun withLastStateAsFlying(block: Flying.() -> Unit) {
        (lastPushedState as? Flying)?.run(block)
    }

    private data class LeverForce(
        val roll: Int = 0,
        val pitch: Int = 0,
        val throttle: Int = 0,
        val yaw: Int = 0
    )
}