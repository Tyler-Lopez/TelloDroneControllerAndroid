package com.tlopez.controllerPresentation

import androidx.lifecycle.viewModelScope
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerPresentation.ControllerViewState.*
import com.tlopez.controllerPresentation.ControllerViewState.Disconnected.*
import com.tlopez.controllerPresentation.ControllerViewState.Connected.*
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnFailure
import com.tlopez.core.ext.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository
) : BaseRoutingViewModel<ControllerViewState, ControllerViewEvent, ControllerDestination>() {

    companion object {
        private const val DELAY_MS_HEALTH_CHECK = 500L
    }

    private var healthCheckJob: Job? = null

    init {
        DisconnectedIdle.push()
        healthCheckLoop()
    }

    override fun onEvent(event: ControllerViewEvent) {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        super.onCleared()
        healthCheckJob?.cancel()
    }

    private fun healthCheckLoop() {
        healthCheckJob?.cancel()
        healthCheckJob = viewModelScope.launch(Dispatchers.IO) {
            healthCheckAction()
            delay(DELAY_MS_HEALTH_CHECK)
            healthCheckLoop()
        }
    }

    private suspend fun healthCheckAction() {
        telloRepository
            .connect()
            .doOnSuccess {
                if (lastPushedState is DisconnectedIdle) {
                    ConnectedIdle().push()
                }
            }
            .doOnFailure {
                if (lastPushedState is Flying || lastPushedState is DisconnectedError) {
                    DisconnectedError
                } else {
                    DisconnectedIdle
                }.push()
            }
    }
}