package com.tlopez.tello_controller.presentation

sealed interface Screen {
    val route: String

    object EnterName : Screen {
        override val route: String = "Welcome"
    }

    object Welcome : Screen {
        override val route: String = "Welcome"
    }
}