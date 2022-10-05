package com.tlopez.tello_controller.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tlopez.tello_controller.data.repository.SocketServiceProvider
import com.tlopez.tello_controller.presentation.theme.FlashcardsAppTheme
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.presentation.controller_screen.ControllerScreen
import com.tlopez.tello_controller.util.TelloCommand
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var socketService: SocketService

    @Inject
    lateinit var socketServerProvider: SocketServiceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ControllerScreen()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(
                this, SocketService::class.java
            ),
            socketServerProvider,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(socketServerProvider)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlashcardsAppTheme {
        Greeting("Android")
    }
}