package com.tlopez.telloShare.domain.usecase

import com.tlopez.telloShare.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
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