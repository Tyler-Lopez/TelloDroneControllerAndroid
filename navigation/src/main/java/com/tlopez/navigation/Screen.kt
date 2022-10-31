package com.tlopez.navigation

sealed interface Screen {
    val route: String

    /** Authorization **/
    object Login : Screen {
        override val route: String = "Login"
    }
    object Register : Screen {
        override val route: String = "Register"
    }
    object VerifyEmail : Screen {
        override val route: String = "VerifyEmail"
    }

    /** Feed **/
    object Welcome : Screen {
        override val route: String = "Welcome"
    }

    /** Flight **/
}