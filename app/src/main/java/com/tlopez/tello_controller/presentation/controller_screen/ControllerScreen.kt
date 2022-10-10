package com.tlopez.tello_controller.presentation.controller_screen

import android.graphics.BitmapFactory
import android.view.SurfaceView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlopez.tello_controller.presentation.thumbstick.Thumbstick

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
                Row {
                    Thumbstick(
                        modifier = Modifier.size(100.dp),
                        onThumbstickDraggedToPercent = {
                            viewModel.onEvent(ChangedMovement(
                                0,
                                0,
                                (it.first * 100).toInt(),
                                (it.second * 100).toInt(),
                            ))
                        }
                    )
                    Thumbstick(
                        modifier = Modifier.size(100.dp),
                        onThumbstickDraggedToPercent = {
                            viewModel.onEvent(ChangedMovement(
                                (it.first * 100).toInt(),
                                (it.second * 100).toInt(),
                                0,
                                0
                            ))
                        }
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
