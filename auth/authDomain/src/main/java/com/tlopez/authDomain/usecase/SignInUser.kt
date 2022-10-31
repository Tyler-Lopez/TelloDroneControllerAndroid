package com.tlopez.authDomain.usecase

import com.tlopez.authDomain.repository.AuthRepository
import javax.inject.Inject

class SignInUser @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): Result<Unit> {
        return authRepository.signIn(username, password)
    }
}