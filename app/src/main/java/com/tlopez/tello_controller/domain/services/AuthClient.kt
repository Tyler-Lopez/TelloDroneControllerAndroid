package com.tlopez.tello_controller.domain.services

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes

interface AuthClient {
    fun init()
    suspend fun signIn(username: String, password: String): Result<Unit>
    suspend fun updatePassword(oldPassword: String, newPassword: String): Result<Unit>
    suspend fun getAccessToken(): Result<String>
    suspend fun fetchUserAttributes(): Result<CognitoUserAttributes>
    suspend fun logout(): Result<Unit>
}