package com.tlopez.telloShare.presentation

sealed interface Screen {
    val route: String

    object EnterName : Screen {
        override val route: String = "EnterName"
    }

    object Login : Screen {
        override val route: String = "Login"
    }

    object Register : Screen {
        override val route: String = "Register"
    }

    object VerifyEmail : Screen {
        override val route: String = "VerifyEmail"
    }

    object Welcome : Screen {
        override val route: String = "Welcome"
    }
}