package com.tlopez.tello_controller.domain.usecase

import com.tlopez.tello_controller.domain.models.AuthenticatedUser
import com.tlopez.tello_controller.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthenticatedUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<AuthenticatedUser> {
        return authRepository.fetchAuthSession()
    }
}