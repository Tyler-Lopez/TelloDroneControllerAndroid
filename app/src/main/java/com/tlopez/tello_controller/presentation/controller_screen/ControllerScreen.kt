package com.tlopez.tello_controller.presentation.controller_screen

import android.graphics.BitmapFactory
import android.view.SurfaceView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.tlopez.tello_controller.presentation.controller_screen.ControllerViewEvent.*
import androidx.hilt.navigation.compose.hiltViewModel

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
}