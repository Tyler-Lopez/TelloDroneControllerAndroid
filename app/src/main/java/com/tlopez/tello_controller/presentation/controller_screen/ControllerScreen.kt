package com.tlopez.tello_controller.presentation.controller_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import com.tlopez.tello_controller.presentation.thumbstick.Thumbstick
import com.tlopez.tello_controller.presentation.thumbstick.ThumbstickState

@Composable
fun ControllerScreen(viewModel: ControllerViewModel = hiltViewModel()) {
    viewModel.viewState.collectAsState().value?.apply {
        Box {
            latestFrame?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Magenta),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Thumbstick(
                        thumbstickState = thumbstickLeft,
                        modifier = Modifier.size(150.dp),
                        onThumbstickDraggedToFloatPercent = {
                            viewModel.onEvent(MovedThrottleYawThumbstick(it))
                        },
                        onThumbstickReleased = {
                            viewModel.onEvent(ResetThrottleYawThumbstick)
                        },
                    )
                    Thumbstick(
                        thumbstickState = thumbstickRight,
                        modifier = Modifier.size(150.dp),
                        onThumbstickDraggedToFloatPercent = {
                            viewModel.onEvent(MovedRollPitchThumbstick(it))
                        },
                        onThumbstickReleased = {
                            viewModel.onEvent(ResetRollPitchThumbstick)
                        },
                    )
                }

                Button(onClick = {
                    viewModel.onEvent(ClickedConnect)
                }) {
                    Text("start service")
                }
                Button(onClick = {
                    viewModel.onEvent(ClickedStartVideo)
                }) {
                    Text("start video")
                }
                Button(onClick = {
                    viewModel.onEvent(ClickedTakeoff)

                }) {
                    Text("send takeoff command")
                }
                Button(onClick = {
                    viewModel.onEvent(ClickedLand)
                }) {
                    Text("send land command")
                }
                Button(onClick = {
                    viewModel.onEvent(ClickedBreak)
                }) {
                    Text("send break command")
                }
                Text(this@apply.telloState.toString())
            }
        }
    }
}
/*
Column {
    Button(onClick = {
        viewModel.onEvent(ClickedConnect)
    }) {
        Text("start service")
    }
    Button(onClick = {
        viewModel.onEvent(ClickedTakeoff)

    }) {
        Text("send takeoff command")
    }
    Button(onClick = {
        viewModel.onEvent(ClickedLand)
    }) {
        Text("send land command")
    }
    Button(onClick = {
        viewModel.onEvent(
            ChangedMovement(
                0, 100, 100, 0
            )
        )
    }) {
        Text("send forward command")
    }

    Button(onClick = {
        viewModel.onEvent(
            ChangedMovement(
                0, 0, 0, 0
            )
        )
    }) {
        Text("send forward command")
    }

    Text(this@apply.telloState.toString())

}
}
}
 */
