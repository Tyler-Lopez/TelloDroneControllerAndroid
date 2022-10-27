package com.tlopez.tello_controller.data.repository

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.tlopez.tello_controller.domain.models.AuthenticatedUser
import com.tlopez.tello_controller.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : AuthRepository {

    init {
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.configure(applicationContext)
    }

    override suspend fun signIn(username: String, password: String): Result<Unit> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Auth.signIn(
                    username,
                    password,
                    { continuation.resume(Result.success(Unit)) }
                ) { continuation.resumeWithException(it) }
            }
        } catch (e: Exception) {
            println("error $e")
            Result.failure(e)
        }
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
                            continuation.resume(Result.success(
                                object : AuthenticatedUser {

                                }
                            ))
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

    override suspend fun registerUser(
        email: String,
        username: String,
        password: String
    ): Result<Unit> {
        return try {
            suspendCoroutine { continuation ->
                val options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), email)
                    .build()
                Amplify.Auth.signUp(
                    username,
                    password,
                    options,
                    {
                        println("success")
                        continuation.resume(Result.success(Unit))
                    },
                    { continuation.resumeWithException(it) }
                )
            }
        } catch (e: Exception) {
            println("error $e")
            Result.failure(e)
        }
    }

    override suspend fun verify(
        username: String,
        confirmationCode: String
    ): Result<Unit> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Auth.confirmSignUp(
                    username,
                    confirmationCode,
                    {
                        println("success")
                        continuation.resume(Result.success(Unit))
                    },
                    { continuation.resumeWithException(it) }
                )
            }
        } catch (e: Exception) {
            println("error $e")
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        TODO("Not yet implemented")
    }
}