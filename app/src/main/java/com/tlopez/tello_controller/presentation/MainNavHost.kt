package com.tlopez.tello_controller.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.tlopez.tello_controller.architecture.Router
import com.tlopez.tello_controller.presentation.loginScreen.LoginScreen
import com.tlopez.tello_controller.presentation.loginScreen.LoginViewModel
import com.tlopez.tello_controller.presentation.registerScreen.RegisterScreen
import com.tlopez.tello_controller.presentation.registerScreen.RegisterViewModel
import com.tlopez.tello_controller.presentation.verifyEmailScreen.VerifyEmailScreen
import com.tlopez.tello_controller.presentation.verifyEmailScreen.VerifyEmailViewModel
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
        composable(
            route = Screen.VerifyEmail.route + "?username={username}&email={email}",
            arguments = listOf(
                navArgument("email") {
                    nullable = true
                    type = NavType.StringType
                },
                navArgument("username") { type = NavType.StringType },
            )
        ) {
            VerifyEmailScreen(viewModel = hiltViewModel<VerifyEmailViewModel>().apply {
                attachRouter(router)
            })
        }
    }
}