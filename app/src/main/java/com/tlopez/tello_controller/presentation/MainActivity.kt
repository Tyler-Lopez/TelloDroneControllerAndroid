package com.tlopez.tello_controller.presentation

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
import com.tlopez.tello_controller.presentation.theme.FlashcardsAppTheme
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.util.TelloCommand

class MainActivity : ComponentActivity() {

    private lateinit var socketService: SocketService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Greeting("Android")
                        Button(onClick = {
                            Intent(this@MainActivity, SocketService::class.java).also {
                                it.putExtra(SocketService.EXTRA_DATA, TelloCommand.Start)
                                startService(it)
                            }
                        }) {
                            Text("start service")
                        }
                        Button(onClick = {
                            Intent(this@MainActivity, SocketService::class.java).also {
                                it.putExtra(SocketService.EXTRA_DATA, TelloCommand.Takeoff)
                                startService(it)
                            }
                        }) {
                            Text("send takeoff command")
                        }
                        Button(onClick = {
                            Intent(this@MainActivity, SocketService::class.java).also {
                                it.putExtra(SocketService.EXTRA_DATA, TelloCommand.Land)
                                startService(it)
                            }
                        }) {
                            Text("send land command")
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, SocketService::class.java).also {
           // bindService(it, )
        }
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