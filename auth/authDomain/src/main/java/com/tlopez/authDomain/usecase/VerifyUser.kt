package com.tlopez.authDomain.usecase

import com.tlopez.authDomain.repository.AuthRepository
import javax.inject.Inject

class VerifyUser @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        confirmationCode: String
    ): Result<Unit> {
        return authRepository.verify(username, confirmationCode)
    }

}