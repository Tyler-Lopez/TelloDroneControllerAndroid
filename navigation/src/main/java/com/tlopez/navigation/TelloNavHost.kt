package com.tlopez.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tlopez.authPresentation.login.LoginScreen
import com.tlopez.authPresentation.login.LoginViewModel
import com.tlopez.authPresentation.register.RegisterScreen
import com.tlopez.authPresentation.register.RegisterViewModel
import com.tlopez.authPresentation.verifyEmail.VerifyEmailScreen
import com.tlopez.authPresentation.verifyEmail.VerifyEmailViewModel
import com.tlopez.navigation.Screen.*
import com.tlopez.navigation.router.AuthRouter

@Composable
fun TelloNavHost(navController: NavHostController) {

    val authRouter = AuthRouter(navController)

    NavHost(
        navController = navController,
        startDestination = Login.route
    ) {
        composable(route = Login.route) {
            LoginScreen(viewModel = hiltViewModel<LoginViewModel>().also {
                it.attachRouter(authRouter)
            })
        }
        composable(route = Register.route) {
            RegisterScreen(viewModel = hiltViewModel<RegisterViewModel>().also {
                it.attachRouter(authRouter)
            })
        }
        composable(
            route = VerifyEmail.route + "?username={username}&email={email}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("email") {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) {
            VerifyEmailScreen(viewModel = hiltViewModel<VerifyEmailViewModel>().apply {
                attachRouter(authRouter)
            })
        }
    }
}