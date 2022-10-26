package com.tlopez.tello_controller.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.tlopez.tello_controller.architecture.Router
import com.tlopez.tello_controller.presentation.enterNameScreen.EnterNameScreen
import com.tlopez.tello_controller.presentation.enterNameScreen.EnterNameViewModel
import com.tlopez.tello_controller.presentation.loginScreen.LoginScreen
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewModel
import com.tlopez.tello_controller.presentation.registerScreen.RegisterScreen
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewModel
import com.tlopez.tello_controller.presentation.welcomeScreen.WelcomeScreen
import com.tlopez.tello_controller.presentation.welcomeScreen.WelcomeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    navController: NavHostController,
    startRoute: String,
    router: Router<MainDestination>
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(viewModel = hiltViewModel<WelcomeViewModel>().apply {
                attachRouter(router)
            })
        }
        composable(route = Screen.EnterName.route) {
            EnterNameScreen(viewModel = hiltViewModel<EnterNameViewModel>().apply {
                attachRouter(router)
            })
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(viewModel = hiltViewModel<RegisterViewModel>().apply {
                attachRouter(router)
            })
        }
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = hiltViewModel<LoginViewModel>().apply {
                attachRouter(router)
            })
        }
    }
}