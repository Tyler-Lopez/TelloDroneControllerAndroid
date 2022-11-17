package com.tlopez.navigation.router

import androidx.navigation.NavController
import com.tlopez.core.architecture.Router
import com.tlopez.navigation.Screen.*
import com.tlopez.navigation.navigateClearBackstack
import com.tlopez.settingsPresentation.SettingsDestination
import com.tlopez.settingsPresentation.SettingsDestination.*

data class SettingsRouter(private val navController: NavController) : Router<SettingsDestination> {
    override fun routeTo(destination: SettingsDestination) {
        when (destination) {
            is NavigateEditProfile -> onNavigateEditProfile()
            is NavigateLogout -> onNavigateLogout()
            is NavigateUp -> onNavigateUp()
        }
    }

    private fun onNavigateEditProfile() {
        navController.navigate(EditProfilePicture.route)
    }

    private fun onNavigateLogout() {
        navController.navigateClearBackstack(Login.route)
    }

    private fun onNavigateUp() {
        navController.navigateUp()
    }
}