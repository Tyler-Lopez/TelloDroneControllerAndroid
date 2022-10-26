package com.tlopez.tello_controller.data.repository

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthSession
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.AuthException.SignedOutException
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.core.Amplify
import com.tlopez.tello_controller.domain.models.AuthenticatedUser
import com.tlopez.tello_controller.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : AuthRepository {

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

    override suspend fun fetchAuthSession(): Result<AuthenticatedUser> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Auth.fetchAuthSession({ auth ->
                    (auth as AWSCognitoAuthSession)
                        .awsCredentials
                        .run {
                            error?.also { continuation.resumeWithException(it) }
                            Result.success(
                                object : AuthenticatedUser {

                                }
                            )
                        }
                }, { continuation.resumeWithException(it) })
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchUserAttributes(): Result<CognitoUserAttributes> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Result<Unit> {
        TODO("Not yet implemented")
    }
}