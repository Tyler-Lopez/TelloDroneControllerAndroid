package com.tlopez.authDomain.usecase

import com.tlopez.authDomain.repository.AuthRepository
import javax.inject.Inject

class ResendVerification @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String): Result<Unit> {
        return authRepository.resendVerificationEmail(username)
    }
}