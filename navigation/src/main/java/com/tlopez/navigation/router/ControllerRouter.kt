package com.tlopez.navigation.router

import androidx.navigation.NavController
import com.tlopez.controllerPresentation.ControllerDestination
import com.tlopez.core.architecture.Router
import com.tlopez.feedPresentation.FeedDestination

data class ControllerRouter(private val navController: NavController) : Router<ControllerDestination> {
    override fun routeTo(destination: ControllerDestination) {
        TODO("Not yet implemented")
    }
}