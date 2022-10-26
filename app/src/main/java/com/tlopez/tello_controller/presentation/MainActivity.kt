package com.tlopez.tello_controller.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tlopez.tello_controller.architecture.Router
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.domain.services.SocketService
import com.tlopez.tello_controller.presentation.MainDestination.*
import com.tlopez.tello_controller.presentation.MainViewState.*
import com.tlopez.tello_controller.presentation.Screen.*
import com.tlopez.tello_controller.presentation.theme.FlashcardsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), Router<MainDestination> {

    @Inject
    lateinit var socketServiceRepository: TelloRepository

    private lateinit var navController: NavHostController


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            navController = rememberAnimatedNavController()
            FlashcardsAppTheme {
                viewModel.viewState.collectAsState().value?.apply {
                    MainNavHost(
                        navController,
                        if (this is Authenticated) {
                            Welcome.route
                        } else {
                            Login.route
                        },
                        this@MainActivity
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, SocketService::class.java),
            socketServiceRepository.serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(socketServiceRepository.serviceConnection)
    }

    override fun routeTo(destination: MainDestination) {
        when (destination) {
            is NavigateController -> navigateController()
            is NavigateEnterName -> navigateEnterName()
            is NavigateWelcome -> navigateWelcome()
        }
    }

    private fun navigateController() {

    }

    private fun navigateEnterName() {

    }

    private fun navigateWelcome() {

    }
}