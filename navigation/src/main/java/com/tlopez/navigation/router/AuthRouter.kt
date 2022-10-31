package com.tlopez.navigation.router

import androidx.navigation.NavController
import com.tlopez.authPresentation.AuthDestination
import com.tlopez.authPresentation.AuthDestination.*
import com.tlopez.core.architecture.Router

data class AuthRouter(private val navController: NavController) : Router<AuthDestination> {

    override fun routeTo(destination: AuthDestination) {
        when (destination) {
            is NavigateLogin -> onNavigateLogin()
            is NavigateRegister -> onNavigateRegister()
            is NavigateUp -> onNavigateUp()
            is NavigateVerifyEmail -> onNavigateVerifyEmail()
            is NavigateWelcome -> onNavigateWelcome()
        }
    }

    private fun onNavigateLogin() {
    }

    private fun onNavigateRegister() {
    }

    private fun onNavigateUp() {
    }

    private fun onNavigateVerifyEmail() {
    }

    private fun onNavigateWelcome() {
    }
}