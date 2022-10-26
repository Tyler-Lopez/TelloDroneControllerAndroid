package com.tlopez.tello_controller.presentation

sealed interface Screen {
    val route: String

    object EnterName : Screen {
        override val route: String = "EnterName"
    }

    object Login : Screen {
        override val route: String = "Login"
    }

    object Welcome : Screen {
        override val route: String = "Welcome"
    }
}