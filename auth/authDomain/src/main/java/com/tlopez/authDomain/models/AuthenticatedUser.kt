package com.tlopez.authDomain.models

interface AuthenticatedUser {
    val email: String
    val username: String
    // todo add isSignedIn here? or make separate issignedinusecase
}