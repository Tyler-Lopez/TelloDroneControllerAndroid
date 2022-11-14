package com.tlopez.controllerPresentation

import androidx.lifecycle.viewModelScope
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerPresentation.ControllerViewEvent.*
import com.tlopez.controllerPresentation.ControllerViewState.Connected
import com.tlopez.controllerPresentation.ControllerViewState.Connected.ConnectedIdle
import com.tlopez.controllerPresentation.ControllerViewState.Connected.Flying
import com.tlopez.controllerPresentation.ControllerViewState.Disconnected.DisconnectedError
import com.tlopez.controllerPresentation.ControllerViewState.Disconnected.DisconnectedIdle
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import com.tlopez.datastoreDomain.repository.models.TelloFlight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.lang.System.currentTimeMillis
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository,
    private val datastoreRepository: DatastoreRepository
) : BaseRoutingViewModel<ControllerViewState, ControllerViewEvent, ControllerDestination>() {

    companion object {
        private const val DELAY_MS_HEALTH_CHECK = 500L
        private const val DELAY_MS_TELLO_STATE = 500L
        private const val MAX_RETRY_COUNT_LAND = 3
        private const val MAX_RETRY_COUNT_TAKEOFF = 3

        private const val CHALLENGE_ID_TEMP = "003ff51d-bbec-4ddc-be32-369fc8c8cfea"
    }

    private var flightStartedMs: Long? = null
    private var healthCheckJob: Job? = null
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

    private fun onToggledVideo() {
        (lastPushedState as? Connected)?.toggleVideo()?.push()
    }

    override fun onCleared() {
        super.onCleared()
        healthCheckJob?.cancel()
        telloStateJob?.cancel()
    }

    private suspend fun attemptLand(retryCount: Int = 0) {
        telloRepository
            .land()
            .doOnSuccess {
                (lastPushedState as? Connected)?.toLanded()?.push()
                // todo make some function to move all logic following land, success or fail
                pendingFlight?.let {
                    datastoreRepository.updateFlight(
                        it,
                        currentTimeMillis() - (it.startedMs.secondsSinceEpoch * 1000)
                    ).doOnSuccess {
                        println("successfully updated flight")
                    }.doOnFailure {
                        println("failured to update flight")
                    }
                }
            }
            .doOnFailure {
                if (retryCount < MAX_RETRY_COUNT_LAND) {
                    attemptLand(retryCount = retryCount + 1)
                }
            }
    }

    private suspend fun attemptTakeOff(retryCount: Int = 0) {
        telloRepository
            .takeOff()
            .doOnSuccess {
                println("Successfully took off.")
                flightStartedMs = currentTimeMillis()
                (lastPushedState as? Connected)?.toFlying()?.push()
                datastoreRepository.insertFlight(
                    username = "temp",
                    startedMs = currentTimeMillis(),
                    lengthMs = 0L,
                    challengeId = CHALLENGE_ID_TEMP
                ).doOnSuccess {
                    println("success")
                    pendingFlight = it
                }.doOnFailure {
                    println("failure to add flight")
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

    private suspend fun healthCheckAction() {
        telloRepository
            .connect()
            .doOnSuccess {
                println("health check response good")
                if (lastPushedState is DisconnectedIdle) {
                    ConnectedIdle().push()
                    telloStateLoop()
                }
            }
            .doOnFailure {
                println("health check response bad")
                flightStartedMs = null
                telloStateJob?.cancel()
                if (lastPushedState is Flying || lastPushedState is DisconnectedError) {
                    DisconnectedError
                } else {
                    DisconnectedIdle
                }.push()
            }
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
            .doOnSuccess {
                (lastPushedState as? Connected)?.updateTelloState(it)?.push()

                pendingFlight?.let { flight ->
                    datastoreRepository.insertFlightData(
                        telloFlightId = flight.id,
                        receivedAtMs = currentTimeMillis() - (flight.startedMs.secondsSinceEpoch * 1000),
                        mpry = 0,
                        pitch = 0,
                        roll = 0,
                        yaw = 0,
                        vgx = 0,
                        vgy = 0,
                        vgz = 0,
                        templ = 0,
                        temph = 0,
                        tof = 0,
                        h = 0,
                        bat = 0,
                        baro = 0f,
                        time = 0,
                        agx = 0,
                        agy = 0,
                        agz = 0
                    ).doOnSuccess {
                        println("success inserting telloflightdata")
                    }.doOnFailure {
                        println("failed to insert telloflightdata")
                    }
                }

            }
            .doOnFailure {
                // No-op
            }
    }
}