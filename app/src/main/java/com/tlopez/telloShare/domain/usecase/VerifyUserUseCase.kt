package com.tlopez.telloShare.domain.usecase

import com.tlopez.telloShare.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        confirmationCode: String
    ): Result<Unit> {
        return authRepository.verify(username, confirmationCode)
    }

}