package com.tlopez.authDomain.usecase

import com.tlopez.authDomain.repository.AuthRepository
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): Result<Unit> {
        return authRepository.registerUser(email, username, password)
    }
}