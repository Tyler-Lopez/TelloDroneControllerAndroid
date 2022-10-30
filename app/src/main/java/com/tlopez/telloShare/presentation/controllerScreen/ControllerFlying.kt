package com.tlopez.telloShare.presentation.controllerScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlopez.telloShare.architecture.EventReceiver
import com.tlopez.telloShare.presentation.theme.Typography
import com.tlopez.telloShare.presentation.thumbstick.Thumbstick

@Composable
fun ControllerViewState.ConnectedViewState.Flying.ControllerFlying(
    eventReceiver: EventReceiver<ControllerViewEvent>
) {
    Button(onClick = {
        eventReceiver.onEventDebounced(ControllerViewEvent.ClickedLand)
    }) {
        Text("LAND")
    }
    Text(
        text = flightLengthMs.toString(),
        style = Typography.h1
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Thumbstick(
            thumbstickState = rollPitchThumbstickState,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            onThumbstickDraggedToFloatPercent = {
                eventReceiver.onEvent(ControllerViewEvent.MovedRollPitchThumbstick(it))
            },
            onThumbstickReleased = {
                eventReceiver.onEvent(ControllerViewEvent.ResetRollPitchThumbstick)
            }
        )
        Spacer(
            modifier = Modifier
                .width(16.dp)
        )

        Thumbstick(
            thumbstickState = throttleYawThumbstickState,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            onThumbstickDraggedToFloatPercent = {
                eventReceiver.onEvent(ControllerViewEvent.MovedThrottleYawThumbstick(it))
            },
            onThumbstickReleased = {
                eventReceiver.onEvent(ControllerViewEvent.ResetThrottleYawThumbstick)
            }
        )
    }
    Text(telloState.toString())
}