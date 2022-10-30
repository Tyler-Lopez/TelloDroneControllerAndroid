package com.tlopez.telloShare.domain.usecase

import com.tlopez.telloShare.domain.models.AuthenticatedUser
import com.tlopez.telloShare.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthenticatedUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<AuthenticatedUser> {
        return authRepository.getUser()
    }
}