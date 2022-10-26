package com.tlopez.tello_controller.domain.repository

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amplifyframework.auth.AuthSession
import com.tlopez.tello_controller.domain.models.AuthenticatedUser

interface AuthRepository {
    suspend fun signIn(username: String, password: String): Result<Unit>
    suspend fun updatePassword(oldPassword: String, newPassword: String): Result<Unit>
    suspend fun getAccessToken(): Result<String>
    suspend fun fetchAuthSession(): Result<AuthenticatedUser>
    suspend fun fetchUserAttributes(): Result<CognitoUserAttributes>
    suspend fun logout(): Result<Unit>
}