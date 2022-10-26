package com.tlopez.tello_controller.data.repository

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amplifyframework.auth.AuthSession
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.tlopez.tello_controller.domain.services.AuthClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthClientImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : AuthClient {

    init {
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.configure(applicationContext)
    }

    override suspend fun signIn(username: String, password: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePassword(oldPassword: String, newPassword: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccessToken(): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAuthSession(): Result<AuthSession> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserAttributes(): Result<CognitoUserAttributes> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Result<Unit> {
        TODO("Not yet implemented")
    }
}