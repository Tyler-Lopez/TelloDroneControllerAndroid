package com.tlopez.controllerPresentation.subscreens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tlopez.controllerPresentation.ControllerViewEvent
import com.tlopez.controllerPresentation.ControllerViewEvent.*
import com.tlopez.controllerPresentation.composable.thumbstick.Thumbstick
import com.tlopez.controllerPresentation.composable.thumbstick.ThumbstickState
import com.tlopez.core.architecture.EventReceiver
import com.tlopez.corePresentation.common.Title

@Composable
fun FlyingScreen(
    thumbstickStateRollPitch: ThumbstickState,
    thumbstickStateThrottleYaw: ThumbstickState,
    eventReceiver: EventReceiver<ControllerViewEvent>
) {
    Title(text = "Flying")
    Button(
        onClick = { eventReceiver.onEvent(ClickedLand) }
    ) {
        Text("End Flight")
    }
    Row {
        Thumbstick(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            thumbstickState = thumbstickStateRollPitch,
            onThumbstickDraggedToFloatPercent = {
                eventReceiver.onEvent(MovedRollPitchThumbstick(it))
            },
            onThumbstickReleased = {
                eventReceiver.onEvent(ResetRollPitchThumbstick)
            }
        )
        Thumbstick(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            thumbstickState = thumbstickStateThrottleYaw,
            onThumbstickDraggedToFloatPercent = {
                eventReceiver.onEvent(MovedThrottleYawThumbstick(it))
            },
            onThumbstickReleased = {
                eventReceiver.onEvent(ResetThrottleYawThumbstick)
            }
        )
    }
}