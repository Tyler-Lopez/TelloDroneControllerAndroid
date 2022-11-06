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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.lang.System.currentTimeMillis
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository
) : BaseRoutingViewModel<ControllerViewState, ControllerViewEvent, ControllerDestination>() {

    companion object {
        private const val DELAY_MS_HEALTH_CHECK = 500L
        private const val DELAY_MS_TELLO_STATE = 500L
        private const val MAX_RETRY_COUNT_LAND = 3
        private const val MAX_RETRY_COUNT_TAKEOFF = 3
    }

    private var flightStartedMs: Long? = null
    private var healthCheckJob: Job? = null
    private var telloStateJob: Job? = null

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
            .doOnSuccess { (lastPushedState as? Connected)?.updateTelloState(it)?.push() }
            .doOnFailure {
                // No-op
            }
    }
}