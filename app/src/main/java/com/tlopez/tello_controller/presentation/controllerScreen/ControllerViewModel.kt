package com.tlopez.tello_controller.presentation.controllerScreen

import androidx.lifecycle.viewModelScope
import com.tlopez.tello_controller.architecture.BaseViewModel
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.presentation.controllerScreen.ControllerViewEvent.*
import com.tlopez.tello_controller.presentation.controllerScreen.ControllerViewState.*
import com.tlopez.tello_controller.presentation.controllerScreen.ControllerViewState.ConnectedViewState.*
import com.tlopez.tello_controller.presentation.thumbstick.ThumbstickState
import com.tlopez.tello_controller.util.TelloCommand.*
import com.tlopez.tello_controller.util.TelloResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository
) : BaseViewModel<ControllerViewState, ControllerViewEvent>() {

    companion object {
        private const val POLL_DELAY_MS_FLIGHT_TIME = 100L
        private const val POLL_DELAY_MS_CONNECTION_CHECK = 500L
        private const val POLL_DELAY_MS_TELLO_STATE = 500L
    }

    private var flightStartedUnixMs: Long = 0
    private var leverForce: LeverForce = LeverForce()
    private var pollConnectionJob: Job? = null
    private var pollLeverForceJob: Job? = null
    private var pollFlightTimeJob: Job? = null
    private var pollTelloStateJob: Job? = null

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

    private fun onClickedConnect() {
        telloRepository.sendTelloCommand(Start) {
            if (it is TelloResponse.Ok) {
                ConnectedIdle().push()
                pollConnectionLoop()
            } else {
                DisconnectedError.push()
            }
        }
    }

    private fun onClickedLand() {
        pollLeverForceJob?.cancel()
        telloRepository.sendTelloCommand(Land) {
            if (it is TelloResponse.Ok) {
                pollFlightTimeJob?.cancel()
                pollTelloStateJob?.cancel()
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
                pollFlightTimeLoop()
                pollLeverForceLoop()
                pollTelloStateLoop()
                pollVideoStream()
                withLastStateAsConnected {
                    Flying(latestFrame, videoOn).push()
                }
            }
        }
    }

    private fun onMovedRollPitchThumbstick(event: MovedRollPitchThumbstick) {
        withLastStateAsFlying {
            val newState = rollPitchThumbstickState.copyWithPercentAdjustment(
                event.movedByPercent.x,
                event.movedByPercent.y
            )
            leverForce = leverForce.copy(
                roll = (newState.fractionHorizontal * 100).toInt(),
                pitch = (newState.fractionVertical * -100).toInt(),
            )
            copy(rollPitchThumbstickState = newState).push()
        }
    }

    private fun onMovedThrottleYawThumbstick(event: MovedThrottleYawThumbstick) {
        withLastStateAsFlying {
            val newState = throttleYawThumbstickState.copyWithPercentAdjustment(
                event.movedByPercent.x,
                event.movedByPercent.y
            )
            leverForce = leverForce.copy(
                yaw = (newState.fractionHorizontal * 100).toInt(),
                throttle = (newState.fractionVertical * 100).toInt(),
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

    private fun onToggleVideo() {
        telloRepository.toggleVideo {
            withLastStateAsConnected {
                copyWithVideoChange(videoOn = it)
            }
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
                // Connection was lost, cancel all polling.
                pollConnectionJob?.cancel()
                pollLeverForceJob?.cancel()
                pollFlightTimeJob?.cancel()
                pollTelloStateJob?.cancel()
                DisconnectedError.push()
            }
        }
    }

    private fun pollLeverForceLoop() {
        pollLeverForceJob = viewModelScope.launch(Dispatchers.IO) {
            pollLeverForce()
            delay(POLL_DELAY_MS_FLIGHT_TIME)
        }
        pollLeverForceJob?.invokeOnCompletion {
            pollLeverForceLoop()
        }
    }

    private fun pollLeverForce() {
        leverForce.apply {
            telloRepository.sendTelloCommand(SetLeverForce(roll, pitch, throttle, yaw)) {}
        }
    }

    private fun pollFlightTimeLoop() {
        pollFlightTimeJob = viewModelScope.launch(Dispatchers.IO) {
            pollFlightTime()
            delay(POLL_DELAY_MS_FLIGHT_TIME)
        }
        pollFlightTimeJob?.invokeOnCompletion {
            pollFlightTimeLoop()
        }
    }

    private fun pollFlightTime() {
        withLastStateAsFlying {
            copy(flightLengthMs = System.currentTimeMillis() - flightStartedUnixMs).push()
        }
    }

    private fun pollTelloStateLoop() {
        pollTelloStateJob = viewModelScope.launch(Dispatchers.IO) {
            pollTelloState()
            delay(POLL_DELAY_MS_TELLO_STATE)
        }
        pollTelloStateJob?.invokeOnCompletion {
            pollTelloStateLoop()
        }
    }

    private fun pollTelloState() {
        telloRepository.receiveTelloState {
            withLastStateAsFlying {
                copy(telloState = it).push()
            }
        }
    }

    private fun pollVideoStream() {
        telloRepository.receiveVideoStream {
            withLastStateAsConnected {
                copyWithVideoChange(latestFrame = it).push()
            }
        }
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