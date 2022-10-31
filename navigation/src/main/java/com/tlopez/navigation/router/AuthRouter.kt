package com.tlopez.navigation.router

import androidx.navigation.NavController
import com.tlopez.authPresentation.AuthDestination
import com.tlopez.authPresentation.AuthDestination.*
import com.tlopez.core.architecture.Router
import com.tlopez.navigation.Screen
import com.tlopez.navigation.navigateClearBackstack

data class AuthRouter(private val navController: NavController) : Router<AuthDestination> {

    override fun routeTo(destination: AuthDestination) {
        when (destination) {
            is NavigateLogin -> onNavigateLogin()
            is NavigateRegister -> onNavigateRegister()
            is NavigateUp -> onNavigateUp()
            is NavigateVerifyEmail -> onNavigateVerifyEmail(destination)
            is NavigateWelcome -> onNavigateWelcome()
        }
    }

    private fun onNavigateLogin() {
        navController.navigateClearBackstack(Screen.Login.route)
    }

    private fun onNavigateRegister() {
        navController.navigate(Screen.Register.route)
    }

    private fun onNavigateUp() {
        navController.navigateUp()
    }

    private fun onNavigateVerifyEmail(destination: NavigateVerifyEmail) {
        val args = buildString {
            append("?username=${destination.username}")
            if (destination.email != null) {
                append("&email=${destination.email}")
            }
        }
        navController.navigate(Screen.VerifyEmail.route + args)
    }

    private fun onNavigateWelcome() {
    }
}