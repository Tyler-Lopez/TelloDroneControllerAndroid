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
    object FlightDetails : Screen {
        override val route: String = "FlightDetails"
    }
    object Home : Screen {
        override val route: String = "Home"
    }

    /** Settings **/
    object Settings : Screen {
        override val route: String = "Settings"
    }

    object EditProfilePicture : Screen {
        override val route: String = "EditProfilePicture"
    }

    /** Flight **/
    object Controller : Screen {
        override val route: String = "Controller"
    }
}