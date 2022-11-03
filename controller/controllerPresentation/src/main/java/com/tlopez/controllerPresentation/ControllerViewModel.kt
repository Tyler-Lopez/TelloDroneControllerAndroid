package com.tlopez.controllerPresentation

import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerDomain.usecase.TelloUseCases
import com.tlopez.core.architecture.BaseRoutingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloUseCases: TelloUseCases
   // private val telloRepository: TelloRepository
) : BaseRoutingViewModel<ControllerViewState, ControllerViewEvent, ControllerDestination>() {
    override fun onEvent(event: ControllerViewEvent) {
        TODO("Not yet implemented")
    }
}