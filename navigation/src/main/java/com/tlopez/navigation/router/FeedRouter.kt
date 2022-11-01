package com.tlopez.navigation.router

import androidx.navigation.NavController
import com.tlopez.core.architecture.Router
import com.tlopez.feedPresentation.FeedDestination

data class FeedRouter(private val navController: NavController) : Router<FeedDestination> {
    override fun routeTo(destination: FeedDestination) {
        TODO("Not yet implemented")
    }
}