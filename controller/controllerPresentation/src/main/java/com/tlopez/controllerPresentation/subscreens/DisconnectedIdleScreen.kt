package com.tlopez.controllerPresentation.subscreens

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.tlopez.corePresentation.theme.Typography

@Composable
fun DisconnectedIdleScreen() {
    Text(
        text = "How to Connect",
        textAlign = TextAlign.Center,
        style = Typography.h4
    )
    Text(
        text = "Turn the aircraft on by pressing the button on the side.",
        textAlign = TextAlign.Center
    )
    Text(
        text = "On your Android device, turn on airplane mode and then connect to the" +
                "Tello-xxxxx network from your device's Wi-Fi settings menu.",
        textAlign = TextAlign.Center
    )
    CircularProgressIndicator()
    Text(
        text = "Attempting to Connect to Device"
    )

}