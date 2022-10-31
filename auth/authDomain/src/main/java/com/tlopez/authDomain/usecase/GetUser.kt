package com.tlopez.authDomain.usecase

import com.tlopez.authDomain.models.AuthenticatedUser
import com.tlopez.authDomain.repository.AuthRepository
import javax.inject.Inject

class GetUser @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<AuthenticatedUser> {
        return authRepository.getUser()
    }
}