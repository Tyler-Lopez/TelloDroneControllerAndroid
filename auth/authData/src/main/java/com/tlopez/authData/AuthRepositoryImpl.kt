package com.tlopez.authData

import android.content.Context
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.tlopez.authDomain.models.AuthenticatedUser
import com.tlopez.authDomain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InvalidClassException
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

    override suspend fun getUser(): Result<AuthenticatedUser> {
        return try {
            val email = getEmail()
            val username = Amplify.Auth.currentUser.username
            suspendCoroutine { continuation ->
                Amplify.Auth.fetchAuthSession({ auth ->
                    (auth as AWSCognitoAuthSession)
                        .run {
                            awsCredentials.error?.also { continuation.resumeWithException(it) }
                            continuation.resume(Result.success(
                                object : AuthenticatedUser {
                                    override val username = username
                                    override val email = email
                                }
                            ))
                        }
                }, { continuation.resumeWithException(it) })
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getEmail(): String {
        return suspendCoroutine { continuation ->
            Amplify.Auth.fetchUserAttributes({ attrs ->
                attrs
                    .find { it.key == AuthUserAttributeKey.email() }
                    ?.let { continuation.resume(it.value) }
            }) {
                continuation.resumeWithException(it)
            }
        }
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
        code: String
    ): Result<Unit> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Auth.confirmSignUp(
                    username,
                    code,
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
        return try {
            suspendCoroutine { continuation ->
                Amplify.Auth.signOut({
                    continuation.resume(Result.success(Unit))
                }) { continuation.resumeWithException(it) }
            }
        } catch (e: Exception) {
            println("error $e")
            Result.failure(e)
        }
    }

    override suspend fun resendVerificationEmail(username: String): Result<Unit> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Auth.resendSignUpCode(username, {
                    continuation.resume(Result.success(Unit))
                }) { continuation.resumeWithException(it) }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}