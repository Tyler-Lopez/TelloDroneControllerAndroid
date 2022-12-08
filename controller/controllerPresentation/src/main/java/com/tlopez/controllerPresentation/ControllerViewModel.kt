package com.tlopez.controllerPresentation

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.tlopez.controllerDomain.FileRepository
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerPresentation.ControllerViewEvent.*
import com.tlopez.controllerPresentation.ControllerViewState.Connected
import com.tlopez.controllerPresentation.ControllerViewState.Connected.ConnectedIdle
import com.tlopez.controllerPresentation.ControllerViewState.Connected.Flying
import com.tlopez.controllerPresentation.ControllerViewState.Disconnected.DisconnectedError
import com.tlopez.controllerPresentation.ControllerViewState.Disconnected.DisconnectedIdle
import com.tlopez.controllerPresentation.composable.thumbstick.ThumbstickState
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.datastoreDomain.models.TelloFlight
import com.tlopez.datastoreDomain.usecase.InitializeFlight
import com.tlopez.datastoreDomain.usecase.InsertFlightData
import com.tlopez.datastoreDomain.usecase.TerminateFlight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository,
    private val datastoreInitializeFlight: InitializeFlight,
    private val datastoreInsertFlightData: InsertFlightData,
    private val datastoreTerminateFlight: TerminateFlight,
    private val fileRepository: FileRepository
) : BaseRoutingViewModel<ControllerViewState, ControllerViewEvent, ControllerDestination>() {

    companion object {
        private const val DELAY_MS_HEALTH_CHECK = 500L
        private const val DELAY_MS_LEVER_FORCE = 100L
        private const val DELAY_MS_TELLO_STATE = 50L
        private const val MAX_RETRY_COUNT_LAND = 3
        private const val MAX_RETRY_COUNT_TAKEOFF = 3
    }

    private var healthCheckJob: Job? = null
    private var leverForceJob: Job? = null
    private var telloStateJob: Job? = null

    private var pendingFlight: TelloFlight? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    private val commandsDispatcher = Dispatchers.IO.limitedParallelism(1)

    init {
        DisconnectedIdle.push()
        healthCheckLoop()
    }

    override fun onEvent(event: ControllerViewEvent) {
        when (event) {
            is ClickedLand -> onClickedLand()
            is ClickedTakeOff -> onClickedTakeOff()
            is ClickedTakePicture -> onClickedTakePicture()
            is MovedRollPitchThumbstick -> onMovedRollPitchThumbstick(event)
            is MovedThrottleYawThumbstick -> onMovedThrottleYawThumbstick(event)
            is ResetRollPitchThumbstick -> onResetRollPitchThumbstick()
            is ResetThrottleYawThumbstick -> onResetThrottleYawThumbstick()
            is ToggledVideo -> onToggledVideo()
        }
    }

    private fun onClickedLand() {
        (lastPushedState as? Connected)?.toLanding()?.push()
        viewModelScope.launch(commandsDispatcher) {
            attemptLand()
        }
    }

    private fun onClickedTakeOff() {
        (lastPushedState as? Connected)?.toTakingOff()?.push()
        viewModelScope.launch(commandsDispatcher) {
            attemptTakeOff()
        }
    }

    private fun onClickedTakePicture() {
        viewModelScope.launch(Dispatchers.IO) {
            (lastPushedState as? Connected)?.apply {
                bitmapLatest?.let {
                    fileRepository
                        .saveBitmap(it)
                        .doOnSuccess {
                            println("Picture saved successfully.")
                        }
                        .doOnFailure {
                            println("Picture not saved successfully.")
                        }
                }
            }
        }
    }

    private fun onMovedRollPitchThumbstick(event: MovedRollPitchThumbstick) {
        (lastPushedState as? Flying)?.run {
            copy(
                thumbstickStateRollPitch = thumbstickStateRollPitch.copyWithPercentAdjustment(
                    event.movedByPercent.x,
                    event.movedByPercent.y
                )
            )
        }?.push()
    }

    private fun onMovedThrottleYawThumbstick(event: MovedThrottleYawThumbstick) {
        (lastPushedState as? Flying)?.run {
            copy(
                thumbstickStateThrottleYaw = thumbstickStateThrottleYaw.copyWithPercentAdjustment(
                    event.movedByPercent.x,
                    event.movedByPercent.y
                )
            )
        }?.push()
    }

    private fun onResetRollPitchThumbstick() {
        (lastPushedState as? Flying)?.copy(thumbstickStateRollPitch = ThumbstickState())?.push()
    }

    private fun onResetThrottleYawThumbstick() {
        (lastPushedState as? Flying)?.copy(thumbstickStateThrottleYaw = ThumbstickState())?.push()
    }

    private fun onToggledVideo() {
        (lastPushedState as? Connected)
            ?.toggleVideo()
            ?.updateBitmap(null)
            ?.apply {
                viewModelScope.launch(commandsDispatcher) {
                    if (videoOn) {
                        telloRepository.setVideoBitmapListener(::onBitmapReceived)
                        telloRepository.videoStart()
                    } else {
                        telloRepository.setVideoBitmapListener(null)
                        telloRepository.videoStop()
                    }
                }
            }
            ?.push()
    }

    private fun onBitmapReceived(bitmap: Bitmap) {
        (lastPushedState as? Connected)?.updateBitmap(bitmap)?.push()
    }

    override fun onCleared() {
        println("On cleared invoked")
        super.onCleared()
        healthCheckJob?.cancel()
        telloStateJob?.cancel()
        viewModelScope.launch(commandsDispatcher) {
            telloRepository.videoStop()
        }
    }

    private suspend fun attemptLand(retryCount: Int = 0) {
        telloStateJob?.cancel()
        healthCheckJob?.cancel()
        telloRepository
            .land()
            .doOnSuccess {
                (lastPushedState as? Connected)?.toLanded()?.push()
                terminatePendingFlight(asSuccess = true)
            }
            .doOnFailure {
                if (retryCount < MAX_RETRY_COUNT_LAND) {
                    attemptLand(retryCount = retryCount + 1)
                } else {
                    terminatePendingFlight(asSuccess = false)
                }
            }
    }

    private suspend fun attemptTakeOff(retryCount: Int = 0) {
        telloRepository
            .takeOff()
            .doOnSuccess {

                println("Successfully took off.")
                (lastPushedState as? Connected)?.toFlying()?.push()
                // On any successful take-off, begin listening to lever force changes
                leverForceLoop()
                datastoreInitializeFlight().doOnSuccess {
                    println("Successfully initialized flight.")
                    pendingFlight = it
                }.doOnFailure {
                    println("Failed to initialize flight.")
                }
            }
            .doOnFailure {
                println("Failed to take off.")
                if (retryCount < MAX_RETRY_COUNT_TAKEOFF) {
                    attemptTakeOff(retryCount = retryCount + 1)
                }
            }
    }

    private fun healthCheckLoop() {
        healthCheckJob?.cancel()
        healthCheckJob = viewModelScope.launch(commandsDispatcher) {
            healthCheckAction()
            delay(DELAY_MS_HEALTH_CHECK)
            healthCheckLoop()
        }
    }

    private var failuresInRow = 0

    private suspend fun healthCheckAction() {
        telloRepository
            .connect()
            .doOnSuccess {
                println("HEALTH CHECK: Success")
                failuresInRow = 0
                if (lastPushedState is DisconnectedIdle) {
                    ConnectedIdle().push()
                    telloStateLoop()
                }
            }
            .doOnFailure {
                failuresInRow++
                println("HEALTH CHECK: Failure")
                if (failuresInRow > 2) {
                    telloStateJob?.cancel()
                    terminatePendingFlight(asSuccess = false)
                    if (lastPushedState is Flying || lastPushedState is DisconnectedError) {
                        DisconnectedError
                    } else {
                        DisconnectedIdle
                    }.push()
                }
            }
    }

    private fun leverForceLoop() {
        leverForceJob?.cancel()
        leverForceJob = viewModelScope.launch(commandsDispatcher) {
            leverForceAction()
            delay(DELAY_MS_LEVER_FORCE)
            if (lastPushedState is Flying) leverForceLoop()
        }
    }

    private suspend fun leverForceAction() {
        (lastPushedState as? Flying)?.apply {
            telloRepository
                .setLeverForce(
                    roll = (thumbstickStateRollPitch.fractionHorizontal * 100).toInt(),
                    pitch = (thumbstickStateRollPitch.fractionVertical * 100).toInt(),
                    throttle = (thumbstickStateThrottleYaw.fractionHorizontal * 100).toInt(),
                    yaw = (thumbstickStateThrottleYaw.fractionVertical * 100).toInt()
                )
        } ?: telloRepository.setLeverForce(0, 0, 0, 0)
    }

    private fun telloStateLoop() {
        telloStateJob?.cancel()
        telloStateJob = viewModelScope.launch(Dispatchers.IO) {
            telloStateAction()
            delay(DELAY_MS_TELLO_STATE)
            telloStateLoop()
        }
    }

    private suspend fun telloStateAction() {
        telloRepository.receiveTelloState()
            .doOnSuccess { data ->
                println("Successfully received tello state id: ${data.missionPadId} x: ${data.missionPadX}. Speed ${data.speedOfX}")
                (lastPushedState as? Connected)?.updateTelloState(data)?.push()
                pendingFlight?.let { flight ->
                    datastoreInsertFlightData(
                        telloFlight = flight,
                        mid = data.missionPadId,
                        x = data.missionPadX,
                        y = data.missionPadY,
                        z = data.missionPadZ,
                        mPitch = data.mPitch,
                        mRoll = data.mRoll,
                        mYaw = data.mYaw,
                        pitch = data.pitch,
                        roll = data.roll,
                        yaw = data.yaw,
                        vgx = data.speedOfX,
                        vgy = data.speedOfY,
                        vgz = data.speedOfZ,
                        templ = data.temperatureLowestCelsius,
                        temph = data.temperatureHighestCelsius,
                        tof = data.timeOfFlightDistanceCm,
                        h = data.heightCm,
                        bat = data.batteryPercentage,
                        baro = data.barometerPressureCm,
                        time = data.timeMotorUsed,
                        agx = data.accelerationX,
                        agy = data.accelerationY,
                        agz = data.accelerationZ
                    ).doOnSuccess {
                        println("SUCCESS. Inserted TelloFlightData")
                    }.doOnFailure {
                        println("FAILURE. Unable to insert TelloFlightData")
                    }
                }
            }
            .doOnFailure {
                // No-op
            }
    }

    private suspend fun terminatePendingFlight(asSuccess: Boolean = false) {
        pendingFlight?.let {
            datastoreTerminateFlight(asSuccess, it)
                .doOnSuccess {
                    println("SUCCESS. Terminated flight with success: $asSuccess")
                }.doOnFailure {
                    println("FAILURE. Unable to terminate flight with success: $asSuccess")
                }
        }
        pendingFlight = null
    }
}