package com.tlopez.controllerPresentation.subscreens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.controllerDomain.TelloState
import com.tlopez.controllerPresentation.ControllerViewEvent
import com.tlopez.core.architecture.EventReceiver
import com.tlopez.corePresentation.common.ScreenBackground
import com.tlopez.corePresentation.common.Title

@Composable
fun ConnectedIdleScreen(
    eventReceiver: EventReceiver<ControllerViewEvent>
) {
    ScreenBackground {
        Title(text = "Connected")
    }
}