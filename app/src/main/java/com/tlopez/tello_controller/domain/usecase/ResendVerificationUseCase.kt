package com.tlopez.tello_controller.domain.usecase

import com.tlopez.tello_controller.domain.repository.AuthRepository
import javax.inject.Inject

class ResendVerificationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String): Result<Unit> {
        return authRepository.resendVerificationEmail(username)
    }
}