package com.tlopez.telloShare.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tlopez.corePresentation.theme.TelloShareTheme
import com.tlopez.navigation.TelloNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /*
    @Inject
    lateinit var socketServiceRepository: TelloRepository


     */
    private lateinit var navController: NavHostController


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //      val viewModel = hiltViewModel<MainViewModel>()
            TelloShareTheme {
                TelloNavHost(navController = rememberAnimatedNavController())
            }
            //   FlashcardsAppTheme {
            //      viewModel.viewState.collectAsState().value?.apply {

            /*
            MainNavHost(
                navController,
                if (this is Authenticated) {
                    Home.route
                } else {
                    Login.route
                },
                this@MainActivity
            )

             */
            //    }
            //      }
        }
    }

    override fun onStart() {
        super.onStart()
        /*
        bindService(
            Intent(this, SocketService::class.java),
            socketServiceRepository.serviceConnection,
            Context.BIND_AUTO_CREATE
        )

         */
    }

    override fun onDestroy() {
        super.onDestroy()
        //unbindService(socketServiceRepository.serviceConnection)
    }

    /*
    override fun routeTo(destination: MainDestination) {
        when (destination) {
            is NavigateController -> navigateController()
            is NavigateLogin -> navigateLogin()
            is NavigateRegister -> navigateRegister()
            is NavigateVerifyEmail -> navigateVerifyEmail(destination)
            is NavigateFeed -> navigateWelcome()
            is NavigateUp -> navigateUp()
        }
    }

    private fun navigateController() {

    }

    private fun navigateLogin() {
        navController.navigate(Login.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    private fun navigateRegister() {
        navController.navigate(Register.route)
    }

    private fun navigateVerifyEmail(destination: NavigateVerifyEmail) {
        val args = buildString {
            append("?username=${destination.username}")
            if (destination.email != null) {
                append("&email=${destination.email}")
            }
        }
        navController.navigate(VerifyEmail.route + args) {
            popUpTo(
                route = Register.route
            ) {
                inclusive = true
            }
        }
    }

    private fun navigateWelcome() {
        navController.navigate(Home.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    private fun navigateUp() {
        navController.navigateUp()
    }

     */
}