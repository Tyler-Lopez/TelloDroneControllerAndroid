package com.tlopez.telloShare.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.tlopez.telloShare.architecture.Router
import com.tlopez.authPresentation.verifyEmail.VerifyEmailScreen
import com.tlopez.authPresentation.verifyEmail.VerifyEmailViewModel
import com.tlopez.telloShare.presentation.welcomeScreen.WelcomeScreen
import com.tlopez.telloShare.presentation.welcomeScreen.WelcomeViewModel

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
            /*
            RegisterScreen(viewModel = hiltViewModel<RegisterViewModel>().apply {
                attachRouter(router)
            })

             */
        }
        composable(route = Screen.Login.route) {
            /*
            LoginScreen(viewModel = hiltViewModel<LoginViewModel>().apply {
                attachRouter(router)
            })

             */
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