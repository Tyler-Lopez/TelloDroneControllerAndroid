package com.tlopez.navigation.router

import androidx.navigation.NavController
import com.tlopez.core.architecture.Router
import com.tlopez.navigation.Screen
import com.tlopez.navigation.navigateClearBackstack
import com.tlopez.settingsPresentation.SettingsDestination
import com.tlopez.settingsPresentation.SettingsDestination.*

data class SettingsRouter(private val navController: NavController) : Router<SettingsDestination> {
    override fun routeTo(destination: SettingsDestination) {
        when (destination) {
            is NavigateLogout -> onNavigateLogout()
        }
    }

    private fun onNavigateLogout() {
        navController.navigateClearBackstack(Screen.Login.route)
    }
}