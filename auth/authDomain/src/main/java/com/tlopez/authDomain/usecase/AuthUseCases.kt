package com.tlopez.authDomain.usecase

data class AuthUseCases(
    val getUser: GetUser,
    val logoutUser: LogoutUser,
    val registerUser: RegisterUser,
    val resendVerification: ResendVerification,
    val signInUser: SignInUser,
    val verifyUser: VerifyUser
)