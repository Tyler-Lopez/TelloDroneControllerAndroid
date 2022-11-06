package com.tlopez.controllerPresentation.subscreens

import android.service.controls.Control
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.controllerPresentation.ControllerViewEvent
import com.tlopez.controllerPresentation.ControllerViewEvent.*
import com.tlopez.core.architecture.EventReceiver
import com.tlopez.corePresentation.common.Title

@Composable
fun FlyingScreen(
    eventReceiver: EventReceiver<ControllerViewEvent>
) {
    Title(text = "Flying")
    Button(
        onClick = { eventReceiver.onEvent(ClickedLand) }
    ) {
        Text("End Flight")
    }
}