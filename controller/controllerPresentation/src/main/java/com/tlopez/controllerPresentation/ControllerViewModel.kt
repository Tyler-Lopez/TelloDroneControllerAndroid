package com.tlopez.controllerPresentation

import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.core.architecture.BaseRoutingViewModel
import javax.inject.Inject

class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository
) : BaseRoutingViewModel<ControllerViewState, ControllerViewEvent, ControllerDestination>() {
    override fun onEvent(event: ControllerViewEvent) {
        TODO("Not yet implemented")
    }
}