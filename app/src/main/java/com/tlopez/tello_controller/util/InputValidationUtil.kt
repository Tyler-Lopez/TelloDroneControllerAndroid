package com.tlopez.tello_controller.util

import android.util.Patterns

class InputValidationUtil {

    companion object {
        private const val MINIMUM_LENGTH_PASSWORD = 6
    }

    fun getEmailValidationMessage(email: String): String? {
        return when {
            email.isBlank() -> "Cannot be empty"
            email.contains(' ') -> "Cannot contain spaces"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    fun getUsernameValidationMessage(username: String): String? {
        return when {
            username.isBlank() -> "Cannot be empty"
            username.contains(' ') -> "Cannot contain spaces"
            else -> null
        }
    }

    fun getPasswordValidationMessage(password: String): String? {
        return when {
            password.isBlank() -> "Cannot be empty"
            password.contains(' ') -> "Cannot contain spaces"
            password.length < MINIMUM_LENGTH_PASSWORD -> "Must be at least 6 characters"
            else -> null
        }
    }
}