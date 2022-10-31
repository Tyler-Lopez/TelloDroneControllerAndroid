package com.tlopez.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tlopez.navigation.Screen.*
import com.tlopez.authPresentation.login.LoginScreen
import com.tlopez.authPresentation.login.LoginViewModel
import com.tlopez.navigation.router.AuthRouter

@Composable
fun TelloNavHost(navController: NavHostController) {
    val authRouter: AuthRouter = AuthRouter(navController)

    NavHost(
        navController = navController,
        startDestination = Login.route
    ) {
        composable(route = Login.route) {
            LoginScreen(viewModel = hiltViewModel<LoginViewModel>().also {
                it.attachRouter(authRouter)
            })
        }
    }
}