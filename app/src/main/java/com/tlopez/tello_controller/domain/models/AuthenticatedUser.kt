package com.tlopez.tello_controller.domain.models

interface AuthenticatedUser {
    val email: String
    val username: String
}